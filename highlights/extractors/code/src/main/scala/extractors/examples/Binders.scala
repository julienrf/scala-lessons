package extractors.examples

object Binders {
  
  case class User(name: String, age: Int)
  
  def test(users: Any) = users match {
    case List(single @ User(_, 20)) => single.name
    case List(_, tail @ _*)         => tail.mkString
    case Nil                        => "nobody"
  }
  
}