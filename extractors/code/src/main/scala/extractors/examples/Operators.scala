package extractors.examples

object Operators {
  
  sealed trait Op
  case class And(lhs: Boolean, rhs: Boolean) extends Op
  case class Or(lhs: Boolean, rhs: Boolean) extends Op
  
  def eval(op: Op) = op match {
    case And(l, r) => l && r
    case l Or r    => l || r
  }
  
  def merge(l1: List[Int], l2: List[Int]): List[Int] = l1 match {
    case x :: xs => merge(xs, x :: l2)
    case Nil     => l2
  }
}