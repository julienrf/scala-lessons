package julienrf.course.gameoflife

object GameOfLife {

  def start() {
    val world = World.random(15, 30)
    val ui = new Ui

    Events.every(1000)
      .fold(world)((_, world) => world.next)
      .foreach { w =>
        println(w)
        ui.updateWorld(w)
      }
  }

}
