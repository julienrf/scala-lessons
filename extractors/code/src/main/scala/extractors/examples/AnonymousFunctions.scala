package extractors.examples

object AnonymousFunctions {

  case class User(name: String, age: Int)

  def usernames(xs: List[Any]): List[String] = xs collect {
    case User(name, _) => name
  }

  def badUsernames(xs: List[Any]): List[String] = xs map {
    case User(name, _) => name
  }

  def safeSqrt: PartialFunction[Double, Double] = {
    case x if x >= 0 => math.sqrt(x)
  }

  def list = List(User("Julien", 25), "Foo", User("Sadek", 31))
}