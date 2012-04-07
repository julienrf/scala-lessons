package parsers

import org.specs2.mutable._

class Exercise5Spec extends Specification {

  val ex5 = new exercises.Exercise5 {}
  import ex5._

  val parser = new LessParser

  "Less parser" should {
    "parse a selector" >> {
      parser.parse(parser.selector, "div > span.foo").get must equalTo (Selector("div > span.foo"))
    }
    "parse a rule" >> {
      parser.parse(parser.property, "color: white;").get must equalTo (Property("color: white;"))
    }
    "parse a block" >> {
      val css =
        """|.foo {
           |  display: none;
           |}""".stripMargin
      parser.parse(parser.block, css).get must equalTo (Block(Selector(".foo "), List(Property("display: none;"))))
    }
    "parse nested blocks" >> {
      val css =
        """|.foo {
           |  display: block;
           |  .bar {
           |    color: white;
           |  }
           |  font-family: serif;
           |}""".stripMargin
      parser.parse(parser.block, css).get must equalTo (
        Block(
          Selector(".foo "),
          List(
            Property("display: block;"),
            Block(
              Selector(".bar "),
              List(
                Property("color: white;")
              )
            ),
            Property("font-family: serif;")
          )
        )
      )
    }
  }
}