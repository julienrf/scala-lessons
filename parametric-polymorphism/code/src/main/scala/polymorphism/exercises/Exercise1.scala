package polymorphism.exercises

object Exercise1 {

  trait List[A] {

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

    def apply[A](xs: A*): List[A] = sys.error("TODO")

  }

}