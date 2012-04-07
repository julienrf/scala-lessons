package extractors.examples

object Types {
  
  def kindOf(x: Any) = x match {
    case b: Boolean => "boolean"
    case n: Int => "integer"
    case r: Double => "real"
    case s: String => "string"
    case _ => "unknown"
  }
  
}