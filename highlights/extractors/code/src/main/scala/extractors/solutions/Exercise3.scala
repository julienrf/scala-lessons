package extractors.solutions

object Exercise3 {

  import Exercise1._
  import Exercise2._
  import dispatch.json._

  object Domains {

    def domains(users: List[JsValue]): List[String] = {
      for {
        User(email, _) <- users
        _ At domain <- Some(email)
      } yield domain
    }

  }

}