package julienrf.course.gameoflife

import javax.swing.JFrame

import doodle.core.{Circle, Image}
import doodle.jvm.DoodlePanel

object Ui {

  def show(events: Events[Image]): Unit = {
    val panel = DoodlePanel(Circle(0) lineWidth 0)
    events.foreach(image => panel.image = image)
    val frame = new JFrame("Game of life")
    frame.getContentPane.add(panel)
    frame.pack()
    frame.setVisible(true)
  }

}
