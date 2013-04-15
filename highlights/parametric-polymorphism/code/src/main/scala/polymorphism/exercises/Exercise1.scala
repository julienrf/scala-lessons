package polymorphism.exercises

object Exercise1 {

  trait List[A] {
    def head: Option[A]
    def tail: List[A]
    def map[B](f: A => B): List[B]
  }

  object List {

    def apply[A](xs: A*): List[A] = sys.error("TODO")

  }

}