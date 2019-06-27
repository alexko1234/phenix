package app;

import java.io._;
import java.io.File;
import scala.io.Source;
import java.util.Date;
import scala.collection.mutable.ListBuffer;

class DAO {

	val transactions = "transactions_";
	val extention = ".data";
	val bestSales = "top_100_ventes_";
	val bigestSales = "top_100_ca_";
	val refprod = "reference_prod-";
	val sevendays = "-J7";
	val dateExtL = 17;
	var dir = "./";
	

	def openFile (name : String) : List[String] = {
  	try{
  	  return Source.fromFile(dir + name).getLines.toList;
	  }catch{
	    case e : ArrayIndexOutOfBoundsException => return null;
	    case fnf : java.io.FileNotFoundException => return null;
	  }
	}	
	
	//get a products list from transactions
	def getTransaction (date : String) : List[String] = {
  	  return openFile(transactions + date + extention);
	}	
	
	
	//get list of 100 best * 1 day files names 
	def selectListNames (dates : List[String], fileType : String) : List[String] = {
	  var names = new ListBuffer[String];
  	for(d  <-  dates){
  	  try{
    	  for(f <- new File(dir).listFiles().map(_.getName())
    	            .filter(_.takeRight(dateExtL).contains(d))
    	            .filter(_.contains(fileType))
    	            .filter(!_.contains(sevendays))){
    	    names += f;
    	  }
  	  }catch{
  	    case e : ArrayIndexOutOfBoundsException => {
  	              e.printStackTrace
  	              println(fileType + " pour le " + d + "ne sont pas retrouvées");
  	              return null;
  	    }
  	  }
	  }
	  return names.toList;
	}
	
	//write new file
	def newFile (name : String, body : String) {
    val pw = new PrintWriter(new File(dir + name + extention));
    pw.write(body);
    pw.close;
    println("new file : " + name);
	}
	
}
