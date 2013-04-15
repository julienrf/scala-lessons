package parsers.solutions

import scala.util.parsing.combinator.JavaTokenParsers

trait Exercise4 extends JavaTokenParsers {

  def number: Parser[Double] = floatingPointNumber ^^ { _.toDouble }

  def factor: Parser[Double] = number | "(" ~> expr <~ ")"

  def term: Parser[Double] = {
    (factor ~ ((("*"|"/") ~ factor)*)) ^^ { case n ~ ops =>
      ops.foldRight(n) {
        case ("*" ~ f, p) => p * f
        case ("/" ~ f, p) => p / f
      }
    }
  }

  def expr: Parser[Double] = {
    (term ~ ((("+"|"-") ~ term)*)) ^^ { case n ~ ops =>
      ops.foldRight(n) {
        case ("+" ~ t, e) => e + t
        case ("-" ~ t, e) => e - t
      }
    }
  }

  def eval(s: String): Option[Double] = parseAll(expr, s) match {
    case Success(value, _) => Some(value)
    case _ => None
  }

}