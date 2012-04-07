package extractors.examples

object StableIds {
  
  sealed trait Action
  object Drink extends Action
  object Eat extends Action
  object Sleep extends Action
  
  def execute(t: Action) = t match {
    case Drink => "slurp"
    case Eat => "crunch"
    case Sleep => "zzZZz"
  }
  
  
  import math.{round, random}
  
  val Mystery = round(random * 3)
  
  def guess(n: Long) = n match {
    case Mystery => "Yeepee!"
    case _ => "Missed…"
  }
  
}