% Scala Course
% Julien Richard-Foy, Zenexity

# Overview

## Why Scala?

- > A problem **well put** is **half solved**.

- > A **scalable** programming language is one in which the **same** concepts can describe **small** as well as **large** parts.

> - Scala is a **general purpose programming language**

## Functional Programming

### Functional idioms

- immutability

- higher-order functions

- pattern matching, algebraic data types

```scala
val inc = (x: Int) => x + 1
val xs = List(1, 2, 3)
val ys = xs map inc // List(2, 3, 4)
```

## Object Oriented

- every value is an object
    - `42.toString`

- every operator is a method call
    - `1 + 2` &hArr; `1.+(2)`
    - `1 to 10` &hArr; `1.to(10)`

- single inheritance

- mixin-based composition mechanism

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

# Start programming

## Read-Eval-Print Loop

The quickest way to try Scala is to use the REPL (Read-Eval-Print-Loop)

```bash
$ scala
Welcome to Scala version 2.9.2 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_17).
Type in expressions to have them evaluated.
Type :help for more information.

scala>
```

(Check that [Scala](http://www.scala-lang.org) is installed on your system)


# Definitions and Evaluation

## What is a program made of?

- A program is the **expression** of the solution of a given **problem**
- We need a way to define and reference the elements of the **problem domain**

## The Simplest Program Elements

- What is “the answer to life the universe and everything”?

```scala
scala> 42
res0: Int = 42
```

- How much is three plus two?

```scala
scala> 3 + 2
res1: Int = 5
```

- What is the result of the concatenation of the texts “Hello ” and “world!”?

```scala
scala> "Hello " ++ "world!"
res2: String = Hello world!
```

## First Building Blocks

- The programming language gives us the ability to:

    - write primitive expressions representing **simple elements** (`42`, `"Hello "`, etc.)

    - **combine** these expressions (using operators)

## (Slightly) Bigger Examples

- What is the circumference of a circle with a radius of 10?

```scala
scala> 2 * 3.14159 * 10
res3: Double = 62.8318
```

- What is the area of the same circle?

```scala
scala> 3.14159 * 10 * 10
res4: Double = 314.159
```

## Naming

- *Hard-coded* values makes the program hard to **read** and to **maintain**

- Hopefully we can use names to refer to values:

```scala
scala> val radius = 10
radius: Int = 10

scala> val pi = 3.14159
pi: Double = 3.14159

scala> val circumference = 2 * pi * radius
circumference: Double = 62.8318

scala> val area = pi * radius * radius
area: Double = 314.159
```

## Functions

- What is the circumference of *any* circle, given its radius?

```scala
def circumference(radius: Double) = 2 * pi * radius
```

- What is the area of *any* circle, given its radius?

```scala
def area(radius: Double) = pi * radius * radius
```

Usage:

```scala
scala> circumference(10)
res7: Double = 62.8318

scala> circumference(20)
res8: Double = 125.6636
```

## First Abstraction Mechanisms

- The programming language gives us the ability to:

    - **abstract** over expressions, by introducing a name to refer to an expression

    - **abstract** over the variable parts of a computation, by making them function parameters

## Exercises

* Write a function `square(x: Double): Double` that returns the square of its parameter `x`

```scala
scala> square(5)
res9: Double = 25.0
```

* Rewrite the `area` function to use `square`

## Conditional Expressions

- What is the absolute value of a number `x`?

```scala
def abs(x: Double) = if (x < 0) -x else x
```

## Syntax Summary (Value Definitions)

### Definitions

- `val <name> = <expr>`

- `def <name>(<p1>, <p2>, …) = <expr>`

### Expressions

- `if (<expr>) <expr> else <expr>`

- `<name>(<expr>, <expr>, …)`

## Recursive Functions

- What is the factorial of an integer `n`?

```scala
def fact(n: Int): Int =
  if (n <= 1) 1
  else n * fact(n - 1)
```

## Exercises

* Write a function `fib(n: Int): Int` that returns the `n`^th^ element of the Fibonacci sequence.

> In the Fibonacci sequence of numbers, each number is the sum of the previous two numbers, starting with 0 and 1. This sequence begins with 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...

```scala
scala> fib(0)
res10: Int = 0

scala> fib(1)
res11: Int = 1

scala> fib(2)
res12: Int = 1

scala> fib(3)
res13: Int = 2
```

## Evaluation model? Termination? Linear and Tail Recursion? Boolean Expressions?

### TODO

## More Exercises?

### TODO


# Making Real Programs

## Making an Executable Program from a Source File

- Write the following program in a file `Main.scala`:

```scala
object Main extends App {

  def fact(n: Int): Int =
    if (n <= 1) 1
    else n * fact(n - 1)

  println(fact(4))

}
```

## Making an Executable Program from a Source File (cont’d)

- Compile it:

```bash
$ scalac Main.scala
```

- Run it:

```bash
$ scala Main
24
```

## Scaling the Build Process

- What if your program has 100 source files?

- How to recompile only the sources impacted by a given modification?

- What if my project depends on a third-party library?

- How to handle a whole project lifecycle (testing, packaging, publishing, …)?

## sbt

[sbt](http://www.scala-sbt.org/) is a build tool for Scala (and Java) projects

*sbt-ize* your project:

- Create a directory `my-project` for your project
- Move your `Main.scala` file into a `my-project/src/main/` directory
- Run it:
    - `$ sbt run` (from `my-project/` directory)
    - sbt automatically compiles the sources and calls the program entry point

## sbt Quick Start

- Run `$ sbt <command>` from your project directory to run an sbt command from your shell

- Run `$ sbt` from your project directory to open the sbt prompt

- Run sbt commands from the sbt prompt

    - `> run` to run your project
    - `> console` to open a Scala REPL in the context of your project
    - `> test` to run the tests of your project
    - `> compile` to compile your project (without running it)

## Using an IDE

Why use an IDE?

- syntax highlighting

- auto-completion

- code navigation

- on-the-fly compilation

- code refactoring

## Using [Eclipse](http://www.eclipse.org)

- Install the [Eclipse Scala plugin](http://scala-ide.org/)

- Create a file `my-project/project/plugins.sbt` with the following content:

```scala
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.2")
```

- Run the `eclipse` sbt command

- In Eclipse: `File` &rarr; `Import…` &rarr; `Existing Projects into Workspace` and select your project directory

## Using [IntelliJ IDEA](http://www.jetbrains.com/idea/)

- Install the [IntelliJ Scala plugin](http://plugins.jetbrains.com/plugin/?id=1347)

- Create a file `my-project/project/plugins.sbt` with the following content:

```scala
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.4.0")
```

- Run the `gen-idea` sbt command

- In IntelliJ: `File` &rarr; `Open…` and select your project directory

## Organizing your Code

- Organize your code in **packages**:

```scala
package myproject.business

object Fibonacci {
  def fib(n: Int) = …
}
```

```scala
package myproject.ui

object Fibonacci {
  def show(n: Int) = …
}
```

## Organizing your Code (cont’d)

- Refer to a value by its *fully qualified name*:

```scala
myproject.business.Fibonacci.fib(6)
```

- Or **import** it:

```scala
import myproject.business.Fibonacci.fib
fib(6)
```


# Scaling Abstractions

## [Euler Problem #1](http://projecteuler.net/problem=1)

> If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
>
> Find the sum of all the multiples of 3 or 5 below 1000.

```scala
def euler1: Int = loop(1, 0)

def isMultipleOf3Or5(n: Int) = n % 3 == 0 || n % 5 == 0

def loop(n: Int, sum: Int): Int =
  if (n < 1000) loop(n + 1, if (isMultipleOf3Or5(n)) sum + n else sum)
  else sum
```

> - the behavior of `loop` makes sense only for `euler1`
    - we don’t want **users** of `euler1` to rely on `loop`

## Blocks, Visibility and Encapsulation

- `isMultipleOf3Or5` and `loop` are **implementation** details for `euler1`

- We can hide them in a block:

```scala
def euler1 = {

  def isMultipleOf3Or5(n: Int) = n % 3 == 0 || n % 5 == 0

  def loop(n: Int, sum: Int): Int =
    if (n < 1000) loop(n + 1, if (isMultipleOf3Or5(n)) sum + n else sum)
    else sum

  loop(1, 0)
}
```

## Lexical Scoping

- Definitions of outer blocks are visible inside a block

- We can rewrite `euler1` as follows:

```scala
def euler1 = {

  def loop(n: Int, sum: Int): Int = {

    def isMultipleOf3Or5 = n % 3 == 0 || n % 5 == 0

    if (n < 1000) loop(n + 1, if (isMultipleOf3Or5) sum + n else sum)
    else sum
  }

  loop(1, 0)
}
```

## Higher-Order Functions

## Data Abstraction

### TODO Remind standard types (Int, Double, String, Boolean)

## Abstract Data Types

You want to design a program manipulating **three dimensional vectors**

A vector has three coordinates (`x`, `y` and `z`)

To compute the cross product of two vectors you could write the following three functions:

```scala
def crossProductX(y1: Int, z1: Int, y2: Int, z2: Int) = y1 * z2 - z1 * y2
def crossProductY(z1: Int, x1: Int, z2: Int, x2: Int) = z1 * x2 - x1 * z2
def crossProductZ(x1: Int, y1: Int, x2: Int, y2: Int) = x1 * y2 - y1 * x2
```

## ???

And you could call these functions as follows to compute the cross product of the vectors `(1, 0, 0)` and `(0, 1, 0)`:

```scala
val x1 = 1; val y1 = 0; val z1 = 0
val x2 = 0; val y2 = 1; val z2 = 0

val x3 = crossProductX(y1, z1, y2, z2)
val y3 = crossProductY(z1, x1, z2, x2)
val z3 = crossProductZ(x1, y1, x2, y2)
```

> - Note how the constituting parts of a same vector would be distant of each other

> - Such a code would be very **error prone** and **hard to read and maintain**

## Defining Classes

You can capture the concept of a three dimensional vector into a **class**:

```scala
class Vector3D(val x: Int, val y: Int, val z: Int) {

  def crossProduct(that: Vector3D) =
    new Vector3D(
      this.y * that.z - this.z * that.y,
      this.z * that.x - this.x * that.z,
      this.x * that.y - this.y * that.x
    )

}

val u = new Vector3D(1, 0, 0)
val v = new Vector3D(0, 1, 0)
val w = u.crossProduct(v)
```

## The Vector3D Class

- `Vector3D` is a **type**

- It is also a **constructor**

    - `x`, `y` and `z` are constructor parameters

    - New **objects** are created by calling the constructor prefixed by `new`

- `crossProduct` is a **member** of the `Vector3D` class

    - `x`, `y` and `z` are also members

    - Members can be selected with the syntax `<object>.<member>`

    - More precisely, `crossProduct` is a **method** because it is a function member

## Exercise

### TODO

## Generalizations and Encapsulation

## Traits

## Dynamic Method Dispatch

## Encapsulation, Maintainability and Modularity

- Encapsulation is a key principle for writing **maintainable** programs, by allowing the construction of **abstraction barriers**

- Encapsulation enables **modularity**: the underlying implementation can change without affecting users

## Abstract Methods, Substitution, Overriding

## Parameters vs. Abstract Methods

## Recursive Types

## Exercise

IntList


# Composition

## Functions Composition (so late?)

## Traits Composition


# ???

## Encapsulation, Abstraction, Modularity, Composition

### Abstraction

names &rarr; functions &rarr; blocks visibility &rarr; higher-order functions &rarr; traits

### Composition

operators &rarr; function composition &rarr; traits


# Object Orientation

## Every Value is an Object

## Functions are Objects

## Infix Notations for Methods

## Singleton Objects


# Type ???

## Type Abstraction

## Type Polymorphism

## Polymorphic Functions

### Upper and Lower Bounds

## Polymorphic Types

### Type Constructors, Variance

## Type Composition

## Tuple Types

## Intersection Types


# Assignment

## `var`

## Pros and Cons


# Advanced Functional Programming

## Algebraic Data Types, Structural Identity, Pattern Matching

## Implicit Parameters, Typeclasses

## `for` Notation

## Lazy vals and by-name Parameters

## Currying?


# Standard Library

## Collections

## Failure Handling

### Option, Either, Try, `try`/`catch`/`throw`


# Type Members

## `type`

## Type Projection

## Path-Dependent Types

# Bibliography

## SICP, coursera
