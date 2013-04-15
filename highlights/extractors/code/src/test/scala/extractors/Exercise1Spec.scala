package extractors

import org.specs2.mutable._
import dispatch.json.Js

class Exercise1Spec extends Specification {

  // TODO dynamically import exercises or solutions
  import exercises.Exercise1._

  "User.unapply" should {
    "extract email and name from a User value" >> {
      val user = new User("sdr@zenexity.com", "Sadek")
      user match {
        case User(email, name) => {
          email must equalTo ("sdr@zenexity.com")
          name must equalTo ("Sadek")
        }
      }
    }
    "extract email and name from a Json value" >> {
      val json = Js(""" { "email": "sdr@zenexity.com", "name": "Sadek" } """)
      json match {
        case User(email, name) => {
          email must equalTo ("sdr@zenexity.com")
          name must equalTo ("Sadek")
        }
      }
      val json2 = Js(""" { "foo": 42, "name": 0 } """)
      json2 match {
        case User(email, name) => failure
        case _ => success
      }
    }
  }
}