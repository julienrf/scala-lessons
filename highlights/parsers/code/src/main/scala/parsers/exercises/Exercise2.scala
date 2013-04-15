package parsers.exercises

import scala.util.parsing.combinator.JavaTokenParsers

trait Exercise2 extends JavaTokenParsers {

  def number: Parser[Any] = sys.error("TODO")
  def term: Parser[Any] = sys.error("TODO")
  def expr: Parser[Any] = sys.error("TODO")

}