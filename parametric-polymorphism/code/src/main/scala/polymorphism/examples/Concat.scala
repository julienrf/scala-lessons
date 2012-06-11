package polymorphism.examples

object Concat {

  def concat1[A](l1: List[A], l2: List[A]) = l1 ++ l2

  def concat2(l1: List[Any], l2: List[Any]) = l1 ++ l2

  val xs1 = List(1, 2, 3)
  val xs2 = List(4, 5, 6)

  val ss1 = List("a", "b", "c")
  val ss2 = List("d", "e", "f")

}
