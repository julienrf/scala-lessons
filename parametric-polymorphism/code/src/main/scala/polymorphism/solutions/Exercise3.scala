package polymorphism.solutions

object Exercise3 {

  sealed trait List[+A] {

    def head: Option[A]

    def tail: List[A]

    def map[B](f: A => B): List[B] = this match {
      case Nil => Nil
      case Cons(h, t) => Cons(f(h), t map f)
    }

  }

  case object Nil extends List[Nothing] {
    def head = None
    def tail = this
  }

  case class Cons[A](private val h: A, private val t: List[A]) extends List[A] {
    def head = Some(h)
    def tail = t
  }

  object List {

    def apply[A](xs: A*): List[A] =
      if (xs.isEmpty) Nil
      else Cons(xs.head, apply(xs.tail: _*))

  }

}
