package julienrf.course

object Sum {

  def sumInts(xs: List[Int]): Int =
    xs.fold(0)(_ + _)

  def sumComplexes(zs: List[Complex]): Complex =
    zs.fold(new Complex(0, 0))(_ add _)

}
