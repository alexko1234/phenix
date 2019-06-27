import org.scalatest._;

import app.Produit;

class ProduitTest extends FunSuite with DiagrammedAssertions {

	test("Produit"){
		var prod1 = new Produit(0,0,0);
		var prod2 = new Produit(-2,45684,758.20);
		var prod3 = new Produit(23841563,-216869,56128);
		var prod4 = new Produit(563,69,-52.0);
		assert(0 === prod1.id);
		assert(2 === prod2.id);
		assert(23841563 === prod3.id);
		assert(563 === prod4.id);
		assert(0 === prod1.qte);
		assert(45684 === prod2.qte);
		assert(0 === prod3.qte);
		assert(69 === prod4.qte);
		assert(0.0 === prod1.some);
		assert(758.20 === prod2.some);
		assert(56128.0 === prod3.some);
		assert(0.0 === prod4.some);
	}

	test("Qte") {
		val prod = new Produit(1,7,10);
		prod.addQte(12);
		prod.addQte(-12);
		assert(19 === prod.qte);
	}

	test("Somme") {
		val prod = new Produit(1,7,10);
		prod.addSome(120.85);
		assert(130.85 === prod.some);
		prod.addQte(-12);
		assert(130.85 === prod.some);
	}

}
