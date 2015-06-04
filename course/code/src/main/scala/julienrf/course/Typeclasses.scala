package julienrf.course

object Typeclasses {

  /**
   * Implement the following generic `sum` method
   */
  def sum[A](as: Seq[A])(implicit numeric: Numeric[A]): A = ???

  /**
   * Implement an instance of `Ordering[Mat]` that compares the area of the mats
   */
  // implicit val matOrdering: Ordering[Mat] = ???

}
