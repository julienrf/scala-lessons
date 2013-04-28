package julienrf.course.gameoflife

import java.awt.event.{ActionEvent, ActionListener}

object GameOfLife {

  def start(size: Int) {
    val world = World.random(size, size * size / 8)
    val ui = new Ui(size)

    Events.every(150)
    //ui.nextClicks
      .fold(world)((_, world) => world.next)
      .foreach { w =>
        val cells = w.cells.fold(List.empty[(Int, Int)]){
          case ((x, y, cell), cs) => if (cell.alive) (x, y) :: cs else cs
        }
        ui.updateWorld(cells)
      }
  }

}
