package parsers.solutions

import scala.util.parsing.combinator.JavaTokenParsers

trait Exercise3 extends JavaTokenParsers {

  def alt[A](p1: => Parser[A], p2: => Parser[A]) = Parser { in =>
    val res1 = p1(in)
    if (res1.successful) res1 else p2(in)
  }

}