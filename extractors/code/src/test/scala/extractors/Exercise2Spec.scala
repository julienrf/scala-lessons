package extractors

import org.specs2.mutable._

class Exercise2Spec extends Specification {

  import exercises.Exercise2.At

  "At" should {
    "extract name and domain of an email" >> {
      "foo@bar.com" match {
        case name At domain => {
          name must equalTo ("foo")
          domain must equalTo ("bar.com")
        }
      }
      "foobar" match {
        case _ At _ => failure
        case _ => success
      }
    }
  }
}