package parsers.exercises

import scala.util.parsing.combinator.JavaTokenParsers

trait Exercise3 extends JavaTokenParsers {

  def alt[A](p1: Parser[A], p2: Parser[A]): Parser[A] = sys.error("TODO")

}