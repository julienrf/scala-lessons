% Scala Course
% Julien Richard-Foy, Zengularity

# Overview

## Why Scala?

> A problem **well put** is **half solved**.

John Dewey


> A **scalable** programming language is one in which the **same** concepts can describe **small** as well as **large** parts.

Martin Odersky


## Scala in a Few Words

Scala is a **general purpose programming language**

Scala is **object oriented**

Scala is **statically typed**

Scala enables both **functional programming** and **imperative programming**

## Hello World

```scala
object Main extends App {
  println("Hello, World!")
}
```

## Flexible Syntax

```scala
"The 'Hello world' string" should {
  "contain 11 characters" in {
    "Hello world" must have size (11)
  }
}
```

```scala
class PingPong extends Actor {
  def receive = {
    case x => sender ! x
  }
}
```

```scala
class ExprParser extends RegexParsers {
  def factor = "[0-9]+".r | "("~expr~")"
  def term = factor~("*" | "/")~factor
  def expr = term~("+" | "-")~term
}
```
