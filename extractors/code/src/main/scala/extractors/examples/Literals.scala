package extractors.examples

object Literals {
  
  def fact(n: Int): Int = n match {
    case 0 => 1
    case n => n * fact(n - 1)
  }
  
}