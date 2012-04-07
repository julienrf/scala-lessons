package extractors

import org.specs2.mutable._

class Exercise3Spec extends Specification {

  import dispatch.json._
  import exercises.Exercise3._

  "Domain" should {
    "extract domain names of emails of users in a Json list" >> {
      val users = List(
        Js("""{"email": "sdr@zenexity.com", "name": "Sadek"}"""),
        Js("""{"name": "Guillaume"}"""),
        Js("""{"email": "julienrf@gmail.com", "name": "Julien"}""")
      )
      Domains.domains(users) must equalTo (List("zenexity.com", "gmail.com"))

      val users2 = List(
        Js("""{"foo": "bar"}"""),
        Js("""{"baz": 42}""")
      )
      Domains.domains(users2) must equalTo (Nil)
    }
  }

}