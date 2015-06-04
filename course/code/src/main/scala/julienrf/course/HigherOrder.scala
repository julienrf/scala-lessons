package julienrf.course

import doodle.core._
import doodle.jvm._

object HigherOrder {

  def stack(image: Int => Image, n: Int): Image =
  if (n == 1) image(n) else image(n) on stack(image, n - 1)


  def spiral(count: Int): Image = ???


  def layout(op: (Image, Image) => Image, image: Int => Image, n: Int): Image = ???


  def sierpinski(count: Int): Image = ???

}
