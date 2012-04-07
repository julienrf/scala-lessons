package parsers

import org.specs2.mutable._

class Exercise2Spec extends Specification {

  val ex2 = new exercises.Exercise2 {}

  "ex2" should {
    "accept well formed expressions" >> {
      ex2.parseAll(ex2.expr, "2 + 3 * 5").successful must beTrue
      ex2.parseAll(ex2.expr, "1 * (5 - 1)").successful must beTrue
    }
    "reject malformed expressions" >> {
      ex2.parseAll(ex2.expr, "(4").successful must beFalse
    }
  }
}