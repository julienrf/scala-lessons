package julienrf.course.gameoflife

import java.awt.event.{ActionEvent, ActionListener}

trait Events[+A] {
  def foreach(f: A => Unit)
  def map[B](f: A => B): Events[B]
  def fold[B](z: B)(f: (A, B) => B): Events[B]
}

object Events {

  def apply[A](g: (A => Unit) => Unit): Events[A] = new Events[A] {

    def foreach(f: A => Unit) {
      g(f)
    }

    def map[B](f: A => B) = Events[B] { publish =>
      foreach(a => publish(f(a)))
    }

    def fold[B](z: B)(f: (A, B) => B) = Events[B] { publish =>
      var state = z
      publish(state)
      foreach { a =>
        state = f(a, state)
        publish(state)
      }
    }
  }


  def every(delay: Int): Events[Unit] =
    Events[Unit] { publish =>
      new javax.swing.Timer(delay, new ActionListener {
        def actionPerformed(e: ActionEvent) {
          publish(())
        }
      }).start()
    }

}