package polymorphism.examples

object Max {

  def max[A](x: A, y: A, o: Ordering[A]): A =
    if (o.compare(x, y) > 0) x else y


  val intOrdering = new Ordering[Int] {
    def compare(x: Int, y: Int) = x - y
  }

  val stringOrdering = new Ordering[String] {
    def compare(x: String, y: String) = x.size - y.size
  }

}
