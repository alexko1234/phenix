package app;

class Produit (var id: Int,
              	var qte : Int = 0,
              	var some : Double = 0.0){
    if(id < 0) {this.id = Math.abs(id)};
    if(qte > 0){ this.qte = qte }else{this.qte = 0}
    if(some > 0){ this.some = some }else{this.some = 0.0}
  
	def addQte (qtt : Int){
	  if(!(qtt < 0)){
	    this.qte += qtt;
	  }else{
	   println("Impossible d'augmenter la quantité de " + qtt);
	  }
	}
	
	def addSome (prix : Double){
	  if(!(prix < 0)){
	    this.some += prix;
	  }else{
	   println("Impossible d'ajouter " + prix);
	  }
	}
	
}
