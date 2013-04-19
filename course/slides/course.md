% Scala Course
% Julien Richard-Foy, Zenexity

# Overview

## Why Scala?

- > A problem **well put** is **half solved**.

- > A **scalable** programming language is one in which the **same** concepts can describe **small** as well as **large** parts.

> - Scala is a **general purpose programming language**

## Functional Programming

- immutability

- higher-order functions, currying

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

## Statically Typed

TODO

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
Welcome to Scala version 2.10.1 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_17).
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

## The Simplest Program Elements (cont’d)

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

> - *Hard-coded* values makes the program hard to **read** and to **maintain**

## Naming

- Hopefully we can define **names** to refer to values:

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

> - By taking the radius as a parameter, the `circumference` function is more **general** than the previous version computing the circumference of circles with radius of 10 only

## Elements of Programming

- The programming language gives us ways to:

    - write literal expressions representing **simple elements** (`42`, `"Hello "`, etc.)

    - **combine** these expressions (using operators)

    - **abstract** over expressions, by introducing a name to refer to an expression

- These means of composition and abstraction give you **expression power** to generalize programs and combine them

## Exercises

* Write a function `square(x: Double): Double` that returns the square of its parameter `x`

```scala
scala> square(5)
res9: Double = 25.0
```

* Rewrite the `area` function using `square`

## Conditional and Boolean Expressions

- What is the absolute value of a number `x`?

```scala
def abs(x: Double) = if (x < 0) -x else x
```

- Boolean literals: `true` and `false`

- Boolean expressions can be combined with `||` (disjonction), `&&` (conjonction), `!` (negation) and the comparison operations (e.g. `==`)

    - `true || false == !false`

## Basic Types

Scala predefines some data types:

`Int`
: a 32-bit signed integer

`Double`
: a 64-bit IEEE-754 floating point number

`Boolean`
: boolean values (`true` or `false`)

`String`
: character strings

## Syntax Summary

### Definitions

- `val <name> = <expr>`

- `def <name>(<p1>, <p2>, …) = <expr>`

### Expressions

- `if (<expr>) <expr> else <expr>`

- `<name>(<expr>, <expr>, …)`

## Evaluation model

Consider the following program:

```scala
def foo = 1 + 1
val bar = 1 + 1
```

What is the difference between `foo` and `bar`?

> - The right hand side of `def`s is evaluated **each** time their name appear in an expression
>     - `foo` refers to the expression `1 + 1`
> - The right hand side of `val`s is evaluated **once**, at the definition site
>     - `bar` refers to the value `2`

## Recursive Functions

- What is the factorial of an integer `n`?

```scala
def fact(n: Int): Int =
  if (n <= 1) 1
  else n * fact(n - 1)
```

> - The return type of recursive functions can not be inferred, it must be explicit

## Functional Loops

```scala
scala> fact(4)
res9: Int = 24
```

- What are the evaluation steps of the `fact(4)` expression?

## Termination

Is the evaluation process guaranteed to terminate?

```scala
def loop: Nothing = loop
```

<!--
## Linear and Tail Recursion

TODO
-->

## Exercise

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

## Exercise

* Write a function `sumInts(a: Int, b: Int): Int` that returns the sum of all the integers between `a` and `b`


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

- How to handle a whole project lifecycle (testing, packaging, publishing, etc.)?

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

## Using an Integrated Development Environment

Why use an IDE?

- syntax highlighting

- type information

- code navigation

- on-the-fly compilation

- auto-completion

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


# Encapsulation and Abstract Data Types

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

## Abstract Data Types

You want to design a program manipulating **complex numbers**

A complex number has a *real part* and an *imaginary part*, it can be expressed in the form *a + b **i***

Complex numbers can be added, substracted, multiplied, divided, etc.

To compute the product of two complex numbers, you could write the following functions:

```scala
def mulR(xR: Double, xI: Double, yR: Double, yI: Double) =
  xR * yR - xI * yI
def mulI(xR: Double, xI: Double, yR: Double, yI: Double) =
  xI * yR + xR * yI
```

## ???

And you could use these functions as follows:

```scala
val xR = 1; val xI = 2 // x = 1 + 2 i
val yR = 3; val yI = 4 // y = 3 + 4 i

val zR = mulR(xR, xI, yR, yI)
val zI = mulI(xR, xI, yR, yI)
```

> - Note how this design forces you to repeat things

> - Such a code would be very **error prone** and **hard to read and maintain**

## Defining Classes

You can capture the concept of a complex number into a **class**:

```scala
class Complex(a: Double, b: Double) {

  val real = a
  val imag = b

  def mul(that: Complex) =
    new Complex(
      this.real * that.real - this.imag * that.imag,
      this.imag * that.real + this.real * that.imag
    )
}

val x = new Complex(1, 2)
val y = new Complex(3, 4)
val z = x.mul(y)
```

## The `Complex` Class

- `Complex` is a **type**
- `Complex` is also a **constructor**
    - `a` and `b` are constructor parameters
    - New **objects** are created by calling the constructor prefixed by `new`
- `mul`, `real` and `imag` are **members** of the `Complex` type
    - Members can be selected with the syntax `<object>.<member>`
    - More precisely, `mul` is a **method** because it is a function member
- On the inside of a class, the name `this` refers to the object on which the code is called

## Exercise

* Add a `plus(that: Complex): Complex` method that adds two complex numbers

* Add a `pow(e: Int): Complex` method that raises a complex number to the power of `b`

## Polar Coordinates

Alternatively, a complex number could be represented using **polar coordinates**: *(r, &phi;)* where *r* is its *absolute value* and *&phi;* its *argument*

```scala
class Complex(r: Double, phi: Double) {

  val abs = r
  val arg = phi

  def mul(that: Complex) =
    new Complex(
      this.abs * that.abs,
      this.arg + that.arg
    )

}
```

## Data Abstraction

For users point of view, multiplying two complex numbers would be the same no matter which implementation is used:

```scala
z = x.mul(y)
```

How to abstract over the implementation of the `Complex` type?

## Abstract Classes and Members

```scala
abstract class Complex {
  def real: Double
  def imag: Double
  def abs: Double
  def arg: Double
  def mul(that: Complex): Complex
}
```

- `Complex` is an **abstract class**

- Its members are **abstract** (they have no body)

## Implementing an Abstract Class

```scala
class ComplexRectangular(a: Double, b: Double) extends Complex {

  val real = a
  val imag = b
  def abs = math.sqrt(a * a + b * b)
  def arg = math.atan2(a, b)

  def mul(that: Complex) =
    new Complex(
      this.real * that.real - this.imag * that.imag,
      this.imag * that.real + this.real * that.imag
    )
}
```

- `ComplexRectangular` **implements** the `Complex` abstract type by implementing its abstract members

## Implementating an Abstract Class (cont’d)

```scala
class ComplexPolar(r: Double, phi: Double) extends Complex {

  def real = r * math.cos(phi)
  def imag = r * math.sin(phi)
  val abs = r
  val arg = phi

  def mul(that: Complex) =
    new Complex(
      this.abs * that.abs,
      this.arg + that.arg
    )
}
```

## Dynamic Method Dispatch

Consider the following program:

```scala
def mulComplexes(x: Complex, y: Complex) = x.mul(y)
```

At runtime, the implementation of the concrete type of `x` (`ComplexRectangular` or `ComplexPolar`) is called

This process is named **dynamic method dispatch**

## Abstract Members, Encapsulation and Modularity

- Abstract members achieve **data abstraction**, another kind of encapsulation

- Encapsulation allows the construction of **abstraction layers**

- It is a key principle to achieve **modularity**: the underlying implementation can change without affecting users

## Substitution

## Overriding

## Parameters vs. Abstract Members

## Recursive Types

A **recursive type** is a data type that may contain values of the same type

They can encode data structures that can grow to arbitrary size (lists, trees, etc.)

Consider for example the following type `Ints` encoding an infinite stream of `Int` values:

```scala
abstract class Ints {
  def value: Int
  def next: Ints
}
```

```scala
def printSomeInts(ints: Ints) = {
  println(ints.value)
  println(ints.next.next.next.value)
}
```

## Recursive Types (2)

You can generate an infinite stream of zeros:

```scala
class Zeros extends Ints {
  val value = 0
  def next = new Zeros
}

printSomeInts(new Zeros) // prints “0”, “0”
```

## Recursive Types (3)

You can generate an infinite stream of successive numbers:

```scala
class Succs(val value: Int) extends Ints {
  def next = new Succs(value + 1)
}

printSomeInts(new Succs(0)) // prints “0”, “3”
```

## `Any`

## Exercise

Implement an abstract data type representing a sequence of integers of arbitrary size:

```scala
abstract class IntList {
  def add(n: Int): IntList
  def toString: String
}
```

The `toString` method should return `"Nil"` for the empty sequence, and `"1 :: 2 :: Nil"` for the sequence {1, 2}


# Higher-Order Functions and Functions Composition

## Abstract Over Computations

### Exercise

* Add to `IntList` a method `sum: Int` that returns the sum of the elements of the list

* Then add a method `product: Int` that returns the product of the elements of the list

> * Note the strong **similarities** between `sum` and `product`

> * Can you write a more general function that could be reused to define `sum` and `product`?

> * TODO More on why it is difficult without higher-order functions

## Higher-Order Functions

- A function that takes another function as a parameter (or returns a function) is a **higher-order function**

- The type `(T1, …, Tn) => R` is the type of a function that takes `n` parameters (of type `T1`, …, `Tn`) and returns a value of type `R`
    - `Int => Int` is the type of a function that takes an `Int` and returns an `Int`

- The value `(t: T1, …, tn: Tn) => <expr>` is a function that takes `n` parameters (`t1`, …, `tn`) and which body is `<expr>`
    - `(a: Int, b: Int) => a + b` is a function that takes two parameters `a` and `b` and returns their sum

## Exercise

* Generalize `sum` and `product` by adding a method `fold(z: Int, op: (Int, Int) => Int): Int` that returns the application of the operation `op` to all the elements of the list (or `z` in the case of the empty list)
    * You should then be able to rewrite `sum` and `product` as follows:

        ```scala
        def sum = fold(0, (s, n) => s + n)
        def product = fold(1, (p, n) => p * n)
        ```

## Functions Composition

Consider the following functions:

```scala
val length = (text: String) => text.length
val isEven = (n: Int) => n % 2 == 0
```

You can use them to define another function that tests if a text has an even length:

```scala
val hasEvenLength = (text: String) => isEven(length(text))
```

But a shorter way consists in writing that `hasEvenLength` is the composition of the functions `isEven` and `length`:

```scala
val hasEvenLength = isEven compose length
```

## Currying?


# Type Composition

## Traits

## Factories

# ???

## Encapsulation, Abstraction, Modularity, Composition, Generalization

### Abstraction

names &rarr; functions &rarr; blocks visibility &rarr; higher-order functions &rarr; traits

### Composition

operators &rarr; function composition &rarr; traits


# Object Orientation

## Every Value is an Object

## Functions are Objects

## Infix Notations for Methods

## Singleton Objects

## Exercise

* Add the alias `++` for the `concat` member of `IntList`

* Add the alias `::` for the `add` member of `IntList`

* Use a singleton object to represent the empty sequence


# Type ???

## Type Abstraction

Until now, you saw how to abstract over values

It is also possible to abstract over **types**

## Exercise

* Add a method `forAll(p: Int => Boolean): Boolean` to `IntList`, that tests if the predicate `p` holds for all the elements of the list

* Then add a method `hasEvenSize: Boolean` that tests if the list has an even size

* Generalize `forAll` and `hasEvenSize` by adding a method `foldBool(z: Boolean, op: (Boolean, Int) => Boolean): Boolean`

> * Note that the implementations of `fold` and `foldBool` are **exactly the same**.

> * Is it possible to write only one `fold` function that would work with both `Int` and `Boolean`?

## Type Polymorphism

Look at the type signatures of `fold` and `foldBool`:

```scala
(Int,     (Int, Int)     => Int)     => Int
(Boolean, (Boolean, Int) => Boolean) => Boolean
```

You could add a `foldString` with the following type signature:

```scala
(String,  (String, Int)  => String)  => String
```

It always follows this pattern:

```scala
(A, (A, Int) => A) => A
```

## Polymorphic Functions

Functions can have **type parameters**:

```scala
def fold[A](z: A, op: (A, Int) => A): A
```

- `A` is a type parameter

- You can then call `fold` as follows:

```scala
def sum = fold[Int](0, (s, n) => s + n)
def forAll(p: Int => Boolean) = fold[Boolean](true, (b, n) => b && p(n))
```

## Exercise

* Make `fold` polymorphic

* Rewrite `sum`, `product`, `forAll` and `hasEvenSize` in terms of `fold`

## Upper and Lower Bounds

## Exercise

* Implement an abstract data type representing a list of `String` elements:

```scala
abstract class StringList {
  def add(str: String): StringList
  def toString: String
}
```

> - Note the strong similarities with `IntList`
>      - As usual, duplicated code should be interpreted as a signal that something should be generalized
>      - In this case, you want to abstract over the type of the elements of the list

## Polymorphic Types

Types can have type parameters

```scala
abstract class List[A] {
  def add(element: A): List[A]
  def toString: String
}
```

> - `List` is a **type constructor**: it takes a type as parameter and yields another type
>     - E.g. `List[Int]`, `List[String]`, etc.

## Exercise

* Write a polymorphic `List[A]` data type:

```scala
abstract class List[A] {
  def add(element: A): List[A]
  def fold[B](z: B, op: (B, A) => A): A
  def toString: String
}
```

## Variance

`List[Int]` and `List[String]` are different types produced by the application of the types `Int` and `String` to the `List` type constructor

## Type Composition

## Tuple Types

## Intersection Types


# Assignment

## `var`

## Pros and Cons


# Advanced Functional Programming

## Algebraic Data Types, Structural Identity, Pattern Matching

Note that `emptyList[Int].add(1) == emptyList[Int].add(1)` evaluates to `false`

## Typeclasses, Implicit Parameters

Extensibility

## `for` Notation

## Lazy vals and by-name Parameters


# Standard Library

## Collections

## Failure Handling

### Option, Either, Try, `try`/`catch`/`throw`


# Type Members

## `type`

## Type Projection

## Path-Dependent Types


# Bibliography

## SICP, coursera, pfpl

