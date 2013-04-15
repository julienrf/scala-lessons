package parsers.solutions

import scala.util.parsing.combinator.JavaTokenParsers

trait Exercise2 extends JavaTokenParsers {

  def number: Parser[Any] = floatingPointNumber | "(" ~ expr ~ ")"
  def term: Parser[Any] = number ~ ((("*"|"/") ~ number)*)
  def expr: Parser[Any] = term ~ ((("+"|"-") ~ term)*)

}