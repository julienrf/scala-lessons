package extractors.solutions

package object Exercise1 {

  class User(val email: String, val name: String)

  object User {

    def unapply(user: User): Option[(String, String)] = Some(user.email, user.name)

    import dispatch.json._
    def unapply(json: JsValue): Option[(String, String)] = json match {
      case JsObject(self) => {
        (self.get(JsString("email")), self.get(JsString("name"))) match {
          case (Some(JsString(email)), Some(JsString(name))) => Some(email, name)
          case _ => None
        }
      }
      case _ => None
    }
  }
}
