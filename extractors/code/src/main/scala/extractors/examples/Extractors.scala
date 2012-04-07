package extractors.examples

object Extractors {
  
  case class User(name: String, age: Int, favThings: List[String])
  
  def info(user: User) = user match {
    case User("Sadek", _, _)         => "You are Sadek!"
    case User(_, n, _) if n < 18     => "You are under 18!"
    case User(_, _, List("Hacking")) => "You are a geek!"
    case _                           => "You are boring!"
  }
  
}