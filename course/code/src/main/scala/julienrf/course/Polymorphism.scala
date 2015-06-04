package julienrf.course

object Polymorphism {

  /**
   * Implement the following method that takes a collection at parameter and returns its number of elements
   */
  def size[A](as: Seq[A]): Int = ???

  /**
   * Implement the following method that concatenates two sequences
   */
  def concat[A](as1: Seq[A], as2: Seq[A]): Seq[A] = ???

  /**
   * Implement the following method that reverses a sequence
   */
  def reverse[A](as: Seq[A]): Seq[A] = ???

}

/**
 * Write a polymorphic Col[A] data type
 */
sealed trait Col[A] {
  def size: Int = ???
  def concat(as: Col[A]): Col[A] = ???
  def reverse: Col[A] = ???
  def map[B](f: A => B): Col[B] = ???
  def filter(p: A => Boolean): Col[A] = ???
  def forall(p: A => Boolean): Boolean = ???
  def exists(p: A => Boolean): Boolean = ???
  def fold[B](b: B)(f: (B, A) => B): B = ???
}

case class Empty[A]() extends Col[A]
case class OneAnd[A](a: A, tail: Col[A]) extends Col[A]
