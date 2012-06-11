package polymorphism

import org.specs2.mutable._

class Exercise1Spec extends Specification {

  import exercises.Exercise1._

  "List" can {
    "be defined" >> {
      List(1, 2, 3) must not (throwA [Throwable])
      List("a", "b", "c") must not (throwA [Throwable])
    }
    "be transformed" >> {
      List(1, 2, 3) map (_ + 1) must equalTo (List(2, 3, 4))
    }
  }
}