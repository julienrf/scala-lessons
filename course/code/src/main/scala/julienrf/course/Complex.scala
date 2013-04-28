package julienrf.course

class Complex(a: Double, b: Double) {

  val real = a
  val imag = b

  def mul(that: Complex) =
    new Complex(
      this.real * that.real - this.imag * that.imag,
      this.imag * that.real + this.real * that.imag
    )

  def add(that: Complex): Complex = ???

  def pow(e: Int): Complex = ???

}