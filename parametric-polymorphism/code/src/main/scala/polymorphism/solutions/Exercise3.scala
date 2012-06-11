package polymorphism.solutions

object Exercise3 {

  trait List[+A] {

    /**
     * Extract the content of this List
     * @return Some(head, tail) or None if this list is empty
     */
    def uncons: Option[(A, List[A])]

    def map[B](f: A => B): List[B]

    override def equals(that: Any) = that match {
      case l: List[_] => l.uncons == uncons
      case _ => false
    }

    override def hashCode = uncons.hashCode

  }

  object List {

    def nil[A]: List[A] = new List[A] {
      def uncons = None

      def map[B](f: A => B) = nil[B]

      override def toString = "Nil"
    }

    def cons[A](head: A, tail: List[A]): List[A] = new List[A] {
      def uncons = Some(head, tail)

      def map[B](f: A => B) = cons(f(head), tail map f)

      override def toString = head.toString + " :: " + tail.toString
    }

    def apply[A](xs: A*): List[A] =
      if (xs.isEmpty) nil
      else cons(xs.head, apply(xs.tail: _*))

  }

}
