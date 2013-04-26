package julienrf.course.gameoflife

case class Cell(alive: Boolean)

object Cell {
  implicit val zero = new Zero[Cell] {
    val zero = Cell(alive = false)
  }
}

case class World (size: Int, cells: SparseMatrix[Cell]) {

  def circular(n: Int): Int =
    if (n < 0) circular(n + size)
    else if (n > size) circular(n - size)
    else n

  def neighbours(x: Int, y: Int): Seq[Cell] =
    for {
      i <- -1 to 1
      j <- -1 to 1
      if !(i == 0 && j == 0)
    } yield cells(circular(i), circular(j))

  def next: World =
    cells.fold(World(size)) {
      case ((x, y, cell), world) =>
        val aliveNeighbours = neighbours(x, y).filter(_.alive).size
        val alive = (cell.alive && (aliveNeighbours == 2 || aliveNeighbours == 3)) || aliveNeighbours == 3
        //if (cell.alive != alive) println(s"State changed at ($x, $y)")
        world.copy(cells = world.cells.updated(x, y)(Cell(alive)))
    }


  override def toString = {
    val buffer = new StringBuilder
    for (x <- 0 until size) {
      for (y <- 0 until size) {
        buffer.append(if (cells(x, y).alive) "#" else " ")
      }
      buffer.append("\n")
    }
    buffer.toString()
  }
}

object World {

  def apply(size: Int): World = World(size, SparseMatrix[Cell])

  def random(size: Int, n: Int): World = {
    (1 to n).foldLeft(World(size)) { (world, n) =>
      world.copy(cells = world.cells.updated(util.Random.nextInt(size), util.Random.nextInt(size))(Cell(alive = true)))
    }
  }

}