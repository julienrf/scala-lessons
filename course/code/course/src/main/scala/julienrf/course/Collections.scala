package julienrf.course

import doodle.core._

object Collections {

  /**
   * @return a sequence whose elements are the elements of `xs` incremented by one
   */
  def inc(xs: Seq[Int]): Seq[Int] = ???

  /**
   * @return a sequence whose elements are the length of the elements of `ss`
   */
  def lengths(ss: Seq[String]): Seq[Int] = ???

  /**
   * Define a method that takes a sequence of Mat and returns their areas if this one is greater than 1000
   */
  // def largeEnough(mats: Seq[Mat]): Seq[Int] = ???

  /**
   * Returns `n` concentric circles
   */
  def circles(n: Int): Seq[Circle] = ???

  /**
   * Returns `n` concentric circles (recursive implementation)
   */
  def circlesRec(n: Int): Seq[Circle] = ???


  def spiral(n: Int): Seq[Image] = ???

  def spiralRec(n: Int): Seq[Image] = ???

  def sum(xs: Seq[Int]): Int = ???

  def product(xs: Seq[Int]): Int = ???

  def stack(images: Seq[Image]): Image = ???

  def stackRec(images: Seq[Image]): Image = ???

  def layout(op: (Image, Image) => Image, images: Seq[Image]): Image = ???

}
