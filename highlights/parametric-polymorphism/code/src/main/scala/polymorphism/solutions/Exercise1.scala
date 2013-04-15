package polymorphism.solutions

object Exercise1 {

  trait List[A] {
    def head: Option[A]
    def tail: List[A]
    def map[B](f: A => B): List[B]
  }

  case class Nil[A]() extends List[A] {
    def head = None
    def tail = this
    def map[B](f: A => B) = Nil[B]()
  }

  case class Cons[A](private val h: A, private val t: List[A]) extends List[A] {
    def head = Some(h)
    def tail = t
    def map[B](f: A => B) = Cons(f(h), t map f)
  }

  object List {

    def apply[A](xs: A*): List[A] =
      if (xs.isEmpty) Nil()
      else Cons(xs.head, apply(xs.tail: _*))

  }

}
