package extractors.examples

object AltGuard {
  
  def f(x: Int) = x match {
    case 0 | 1 | 2   => "less than 3"
    case n if n < 10 => "between 3 and 9"
    case _           => "more than 9"
  }
  
}