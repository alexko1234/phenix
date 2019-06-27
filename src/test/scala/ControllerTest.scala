import org.scalatest._;

import app.Controller;

class ControllerTest extends FunSuite with DiagrammedAssertions {

	test("setDate") {
		val ctrl = new Controller();
		assert(!ctrl.setDate("25"));
		assert(!ctrl.setDate("56085251"));
		assert(!ctrl.setDate("20270514"));
		assert(!ctrl.setDate("2019/05/14"));
		assert(ctrl.setDate("20190514"));
	}

	test("getSevenDays") {
		val ctrl = new Controller()
		ctrl.setDate("20190105");
		assert("List(20190105, 20190104, 20190103, 20190102, 20190101, 20181231, 20181230)" === ctrl.getSevenDays().toString());
	}

}
