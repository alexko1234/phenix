package app;

import java.time.LocalDate;
import java.time.format._;
import java.text.SimpleDateFormat;
import scala.collection.mutable.ListBuffer;
import scala.util.matching.Regex;

import scala.io.StdIn.readLine;
   
class Controller (){
  	
	val dao : DAO = new DAO();
  val dateFormat = new SimpleDateFormat("yyyyMMdd");
  val formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	var date : String = LocalDate.now().format(formatter);
	
	//check if string is date and it's less from today ; set date today if nothing
	def setDate (dt: String) : Boolean = {
	  val r = raw"(\d{4})(\d{2})(\d{2})".r
	  dt match { case r(year, month, day) => 
                  if (month.toInt < 13 && month.toInt > 0 && day.toInt < 31 && month.toInt > 0 ){
                    if(LocalDate.parse(date, formatter).isAfter(LocalDate.parse(dt, formatter)) || date == dt){
                      date = dt;
                      return true; 
                    }
                  }
                  return false;
	             case "" =>  
                  date = LocalDate.now().format(formatter);
                  return true; 
	             case _ => 
                  return false; }
	  
	}
	
	def start(){
	  var dt : String = "";
	  var path : String = "";
	    do {
	      dt = readLine("Si le traitement pour une date inférieure à celle d’aujourd’hui : \n - Veuillez entrer la date en format : YYYYMMDD - ");
	    }while(!setDate(dt));
	    
      path  = readLine("Veuillez entrez le chemin ver le dossier avec data - ");
      if(path != ""){
        dao.dir = path;
        if(dao.dir.takeRight(1)!= "/"){dao.dir = dao.dir + "/"};
      }
      
      if (oneDayMag()){
        
        if(sevenDaysMagCA()){
          
          println("Le traitement est terminé");
        }
        
      }
	}
	
  def oneDayMag() : Boolean = {
	  val data : List[String] = dao.getTransaction(date);
	  var newData  = new ListBuffer[Produit]();
	  var best : String = "";
	  var ca : String = "";
	  var ref : List[String] =null;
	  var price : Double = 0.0;
	  var magid : String = "";
	  var id : Int = 0;
	  
	  if (data!=null){
	    //for every store
      for ((m,mag) <- data.groupBy(_.split('|')(2))){
        magid = mag(0).split('|')(2);
        newData.clear();
        best = "";
        ca = "";
        ref = dao.openFile(dao.refprod + magid + "_" + date + dao.extention);
        
        //by product
        for((k, prod) <- mag.groupBy(_.split('|')(3))){
          id = prod(0).split('|')(3).toInt;
          if(ref!=null){
            try{ //set price
              price = ref.filter(_.split('|')(0).toInt == id)(0).split('|')(1).toDouble;
            }catch{
              case e : java.lang.IndexOutOfBoundsException => {
                price = 0;
                println("Le prix pour le produit " + id + " dans " + magid + " n'est pas retrouvé ou 0");
              }
            }
          }else{
            price = 0;
          }
          
          //create product
          var produit : Produit = new Produit(prod(0).split('|')(3).toInt);
          //set qte
          for(p <-prod){
            //if(p.split('|')(4).toInt != 0)
              produit.addQte(p.split('|')(4).toInt);
          }
          //set price
          if(price != 0 ) { produit.addSome((price * produit.qte * 100).round / 100.toDouble) }
          
          newData += produit;
        }
        
        if(newData.size > 0){
          //select best 100 
          for(b <- newData.sortWith(_.qte < _.qte).takeRight(100)){
            best = best + b.id + "|" + b.qte + "\n";
          }
          dao.newFile(dao.bestSales + magid + "_" + date, best);
          
          if (ref != null){
            for(b <- newData.sortWith(_.some < _.some).takeRight(100)){
              ca = ca + b.id + "|" + b.some + "\n";
            }
            dao.newFile(dao.bigestSales + magid + "_" + date, ca);
          }else{
            println("Le fichier de references pour " + magid + " n'est pas retrouvé");
          }
        }
        
      }
      
    }else{
      println("Le fichier de transaction n'est pas retrouvé");
      return false;
    }
	  return true;
  }
  
  def sevenDaysMagCA() : Boolean = {
	  val data : String = "";
	  var newData  = new ListBuffer[Produit]();
	  var produit : Produit= new Produit(0);
	  var body : String = "";
	  var id : Int = 0;
	  
	  //select files - best 100 for 7 days
    val filesList = dao.selectListNames(getSevenDays(),dao.bigestSales);
    if(filesList != null && filesList.length != 0){
      // by store
      for((ms, mag) <- filesList.groupBy(_.split('_')(3))){
        newData.clear();
        body = "";
        
        for(f <- mag){

          //by product
          for(prod <- dao.openFile(f)){
            id = prod.split('|')(0).toInt;
            if(!newData.exists{_.id  == id}){
              //create new product
              produit = new Produit(id);
              produit.addSome(prod.split('|')(1).toDouble);
              newData += produit;
            }else{
              //update product
              newData.filter(_.id == id)(0)
                .addSome(prod.split('|')(1).toDouble);
            }
          }
        }
        
        //select best 100
        for(best <- newData.sortWith(_.some < _.some).takeRight(100)){
          body = body + best.id + "|" + best.some + "\n";
        }
	      dao.newFile(dao.bigestSales + mag(0).split('_')(3) + "_" + date + dao.sevendays, body);
      }   
    }else{
      println("Les fichiers de " + dao.bigestSales + " ne sont pas retrouvés");
      return false;
    }
    return true;
  }
  
  //retrieve a list of 6 days preceding the given date
  def getSevenDays() : List[String] = {
    var indates  = new ListBuffer[String]();
	  for(n <- 0 to 6){
	    indates += LocalDate.parse(date, formatter).minusDays(n).format(formatter);
	  }
	  return indates.toList;
  }
  
}
	
