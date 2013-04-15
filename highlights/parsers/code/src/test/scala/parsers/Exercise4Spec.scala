package parsers

import org.specs2.mutable._

class Exercise4Spec extends Specification {

  val ex4 = new exercises.Exercise4 {}

  "ex4" should {
    "evaluate well formed expressions" >> {
      ex4.eval("2 + 3 * 5") must equalTo (Some(17))
      ex4.eval("1 * (5 - 1)") must equalTo (Some(4))
    }
    "reject malformed expressions" >> {
      ex4.eval("(4") must equalTo (None)
    }
  }
}