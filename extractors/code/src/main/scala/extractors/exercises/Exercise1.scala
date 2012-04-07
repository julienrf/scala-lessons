package extractors.exercises

package object Exercise1 {

  class User(val email: String, val name: String)

  object User {

    def unapply(user: User): Option[(String, String)] = sys.error("TODO")

    import dispatch.json._
    /*
      sealed trait JsValue
      case class JsObject(self: Map(JsString, JsValue)) extends JsValue
      case class JsString(self: String) extends JsValue
    */

    def unapply(json: JsValue): Option[(String, String)] = sys.error("TODO")

  }
}
