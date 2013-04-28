package julienrf.course.gameoflife

import java.awt.{Dimension, BorderLayout, Color, Graphics}
import javax.swing.JFrame
import java.awt.event.{ActionEvent, ActionListener}

class Ui(n: Int) {

  val Pixel = 6

  private var currentWorld = List.empty[(Int, Int)]

  private val frame = new javax.swing.JFrame("Game of Life")

  private val panel = new javax.swing.JPanel {

    override def paintComponent(g: Graphics) {
      super.paintComponent(g)
      for {
        x <- 0 until n
        y <- 0 until n
      } {
        g.setColor(if (currentWorld contains (x, y)) Color.black else Color.white)
        g.fillRect(Pixel * x, Pixel * y, Pixel, Pixel)
      }
    }

    override def preferredSize = new Dimension(Pixel * n, Pixel * n)

  }

  val next = new javax.swing.JButton("Next")

  frame.add(panel, BorderLayout.CENTER)
  //frame.add(next, BorderLayout.SOUTH)
  frame.pack()

  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.setVisible(true)

  /**
   * @param world List of pairs (x, y) containing the coordinates of alive cells
   */
  def updateWorld(world: List[(Int, Int)]) {
    currentWorld = world
    frame.repaint()
  }

  /**
   * @return Stream of click events
   */
  def nextClicks = Events[Unit] { publish =>
    next.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        publish(())
      }
    })
  }

}
