package julienrf.course.gameoflife

class SparseMatrix[+A] private (z: A, rows: Map[Int, Map[Int, A]]) {

  def apply(x: Int, y: Int): A =
    (for {
      cols <- rows.get(x)
      cell <- cols.get(y)
    } yield cell) getOrElse z

  def updated[AA >: A](x: Int, y: Int)(a: AA): SparseMatrix[AA] = {
    val cols = rows.getOrElse(x, Map.empty)
    new SparseMatrix(z, rows.updated(x, cols.updated(y, a)))
  }

  def fold[B](z: B)(f: ((Int, Int, A), B) => B): B =
    (for {
      (x, cols) <- rows
      (y, a) <- cols
    } yield (x, y, a)).foldLeft(z)((b, a) => f(a, b))

  override def toString = rows.toString()

}

object SparseMatrix {
  def apply[A : Zero]: SparseMatrix[A] = new SparseMatrix(Zero[A].zero, Map.empty)
}

trait Zero[A] {
  def zero: A
}

object Zero {
  def apply[A : Zero] = implicitly[Zero[A]]

  implicit val intZero = new Zero[Int] {
    val zero: Int = 0
  }
}