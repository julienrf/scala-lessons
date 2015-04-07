package julienrf.course

import doodle.core._

object Loops {

  /**
   * Draw five barbells stacked on top of each other
   */
  def barbells(n: Int): Image = ???

  /**
   * Define a method circles that takes a number `n` as parameter and returns an image
   * showing `n` concentric circles of increasing sizes
   */
  def circles(n: Int): Image = ???

  /**
   * Define a method spiral that takes a number `n` as parameter and returns an image showing `n` circles of
   * increasing sizes and laid out in a shape of spiral
   */
  def spiral(n: Int): Image = ???

  /**
   * Define a method sierpinski that takes a number n as parameter and returns an image showing the
   * Sierpinski triangle at `n` iterations
   */
  def sierpinski(n: Int): Image = ???

  /**
   * Superpose `n` images
   */
  def stack(image: Int => Image, n: Int): Image =
    if (n == 1) image(n) else image(n) on stack(image, n - 1)


  def allAbove(image: Int => Image, n: Int): Image = ???

  def layout(op: (Image, Image) => Image, image: Int => Image, n: Int): Image = ???

}
