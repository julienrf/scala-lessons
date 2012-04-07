package parsers.exercises

import scala.util.parsing.combinator.JavaTokenParsers

trait Exercise4 extends JavaTokenParsers {

  def number: Parser[Double] = sys.error("TODO")
  def factor: Parser[Double] = sys.error("TODO")
  def term: Parser[Double] = sys.error("TODO")
  def expr: Parser[Double] = sys.error("TODO")

  def eval(s: String): Option[Double] = parseAll(expr, s) match {
    case Success(value, _) => Some(value)
    case _ => None
  }

}