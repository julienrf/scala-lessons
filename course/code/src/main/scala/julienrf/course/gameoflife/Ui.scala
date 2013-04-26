package julienrf.course.gameoflife

import java.awt.{Color, Graphics}
import javax.swing.JFrame

class Ui {

  val Pixel = 15

  private var currentWorld: World = _

  val frame = new javax.swing.JFrame("Game of Life")

  val panel = new javax.swing.JPanel {

    override def paintComponent(g: Graphics) {
      super.paintComponent(g)
      for {
        x <- 0 until currentWorld.size
        y <- 0 until currentWorld.size
      } {
        g.setColor(if (currentWorld.cells(x, y).alive) Color.black else Color.white)
        g.fillRect(Pixel * x, Pixel * y, Pixel * x + Pixel - 1, Pixel * y + Pixel - 1)
      }
    }
  }

  frame.add(panel)
  frame.pack()

  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.setVisible(true)

  def updateWorld(world: World) {
    panel.setSize(Pixel * world.size, Pixel * world.size)
    frame.setSize(Pixel * world.size, Pixel * world.size)
    currentWorld = world
    frame.repaint()
    println(s"updating world")
  }


}
