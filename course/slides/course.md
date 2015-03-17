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


# Start programming

## Read-Eval-Print Loop

The quickest way to try Scala is to use the REPL (Read-Eval-Print-Loop)

```bash
$ sbt console
Welcome to Scala version 2.11.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_40).
Type in expressions to have them evaluated.
Type :help for more information.

scala> 
```

(Check that [sbt](http://www.scala-sbt.org) is installed on your system)


# Values and Types

## What is a program made of?

- A program is the **expression** of the solution of a given **problem**

- We need a way to define and reference the elements of the **problem domain**

## The Simplest Program Elements: Literal Values

> What is “the answer to life the universe and everything”?

```scala
scala> 42
res0: Int = 42
```

> What is my name?

```scala
scala> "Julien"
res1: String = Julien
```

- `42` and `"Julien"` are **values** composed of a single **literal value**.

## Compound Values: Operators

> How many is three plus two?

```scala
scala> 3 + 2
res2: Int = 5
```

> What is the result of the concatenation of the texts “Hello ” and “world!”?

```scala
scala> "Hello " ++ "world!"
res3: String = Hello world!
```

- Values can be combined using **operators** to build more complex values.

## Compound Values: Method Calls

> What is the size of the text “Hello world!”?

```scala
scala> "Hello world!".size
res4: Int = 12
```

- **Methods** are **applied** on values using the **dot notation**.

> What is the range of numbers between 1 and 10?

```scala
scala> 1.to(10)
res5: scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
```

- Methods can have **parameters**. They are supplied between parentheses.

## Operators Are Methods

Actually, operators are just methods with symbolic names:

```scala
scala> 3.+(2)
res6: Int = 5
```

The **infix syntax** can be used with non-symbolic methods too:

```scala
scala> 1 to 10
res7: scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
```

- The **unification** of methods and operators makes the language simpler.

## Exercises

- Use the `abs` method to get the absolute value of `-42`.

- Use the `toUpperCase` method to get the text “Hello world!” in upper case.

## Types

- All values have a **type**:
    - `42` has type `Int`,
    - `"foo"` has type `String`.


- Types **classify** values;
    - `0` and `42` are both `Int` values.

## Types Guide You

- Type checking forbids you to combine values in a wrong way:

```scala
scala> 1 to "10"
<console>:20: error: type mismatch;
 found   : String("10")
 required: Int
              1 to "10"
                   ^
```

- Program execution is a two-step process:
    - First, the compiler transforms your Scala code into JVM bytecode,
    - Second, the JVM runs the bytecode.
- Type checking happens during the first step: errors are detected early.

## Some Common Types

`Int`
: a 32-bit signed integer

`Double`
: a 64-bit IEEE-754 floating point number (e.g. `12.34`).

`Boolean`
: boolean values (`true` and `false`)

`String`
: text

## Raising the Abstraction Level: Non-Literal Objects

Until now you worked essentially with numbers and text. But how to define things of a higher-level like **images**?

```scala
scala> Circle(42)
res8: doodle.core.Circle = Circle(42.0)
```

`Circle` is a **constructor** that takes one parameter (the radius) and returns a object representing a circle.

```scala
scala> Rectangle(30, 50)
res9: doodle.core.Rectangle = Rectangle(30.0, 50.0)
```

## Manipulating Images: Display and Layout

- Show an image with `draw`:

```scala
scala> draw(Rectangle(30, 50))

```

- Layout images by combining them using `beside`, `above` and `under`:

```scala
scala> Rectangle(60, 100) beside Circle(30)
res10: doodle.core.Image = Beside(Rectangle(60.0,100.0),Circle(30.0))

scala> draw(Rectangle(60, 100) beside Circle(30))

```

## Exercise

- Draw an exclamation mark.

![](exclamation.png)

## Exercise

- Draw a barbell.

![](barbell.png)

## Manipulating Images: Colors

Use `fillColor` to fill an image with a given color:

```scala
draw(Rectangle(30, 80) fillColor Color.black)
```

Examples of available colors are `red`, `blue`, `green`, `black`, `white`, `gray` and `brown`.

## Exercise

- Add colors to your barbell.

![](barbell-color.png)

# Definitions

## Value Definitions

Consider the following program:

```scala
draw(
  (Rectangle(25, 100) fillColor Color.black) beside
  (Rectangle(200, 20) fillColor Color.grey) beside
  (Rectangle(25, 100) fillColor Color.black)
)
```

- It is hard to **read** because the expression is huge;
- It is hard to **maintain**: if you want to change the width of the weights you have to change the code at **two** places.

## Value Definitions (2)

```scala
val weight = Rectangle(25, 100) fillColor Color.black

val bar = Rectangle(200, 20) fillColor Color.grey

draw(weight beside bar beside weight)
```

- The first two lines are **value definitions**. They:
    - introduce new **names** (`weight` and `bar`),
    - **bind** them to the value at the right of “=”.

- Names can be used as any other value.

- Note that the type of definitions is **inferred** by the compiler.

## Value Definitions

Now, changing the weights requires to modify the code at one place only:

```scala
val weight = Rectangle(15, 100) fillColor Color.black

val bar = Rectangle(200, 20) fillColor Color.grey

draw(weight beside bar beside weight)
```

## Exercise

- Draw two barbells one above the other. The first one must have small weights, while the second one must have heavy weights.

![](barbells.png)

## Method Definitions

Consider the following program:

```scala
val heavyWeight = Rectangle(30, 100) fillColor Color.black
val smallWeight = Rectangle(15, 100) fillColor Color.black
val bar = Rectangle(200, 20) fillColor Color.grey

val smallBarbell = smallWeight beside bar beside smallWeight
val heavyBarbell = heavyWeight beside bar beside heavyWeight

draw(smallBarbell above heavyBarbell)
```

Note the similarities between the `heavyWeight` and `smallWeight` definitions. How could you **generalize** them?

## Method Definitions (2)

```scala
def weight(width: Int) = Rectangle(width, 100) fillColor Color.black

val heavyWeight = weight(30)
val smallWeight = weight(15)
```

- The first line is a **method definition**. Contrary to value definitions, method definitions can have **parameters**.
- Parameters are specified between parentheses, separated by a comma.
- Parameter’s type must be explicitly given.

## Abstraction Principle

> Each significant piece of functionality in a program should be implemented in just one place in the source code.
>
> Where similar functions are carried out by distinct pieces of code, it is generally beneficial to combine them into one by **abstracting** out the varying parts.

Benjamin C. Pierce. *Types and Programming Languages*. MIT Press 2002.

## Exercise

- Define a method `barbell` that takes as parameter an image of a weight and returns an image of a barbell with this weights.

- Rewrite your previous program using the methods `barbell` and `weight`.

<!--
def barbell(weight: Image) =
  weight beside (Rectangle(200, 20) fillColor Color.grey) beside weight

def weight(width: Int) = Rectangle(width, 100) fillColor Color.black

val heavyBarbell = barbell(weight(30))
val smallBarbell = barbell(weight(15))

draw(smallBarbell above heavyBarbell)
-->

## Elements of Programming

- The programming language gives us ways to:

    - write literal values representing **simple elements** (`42`, `"Hello "`, etc.)

    - **combine** these values (using methods)

    - **abstract** over the value of expressions, by introducing a name to refer to an expression

- These means of abstraction and composition give you **expression power** to generalize programs and combine them

# Loops and Conditionals

## Exercise

- Draw five barbells stacked on top of each other.

## Loops and Conditionals

Consider the following program:

```scala
val unit = barbell(weight(15))
val barbell1 = unit
val barbell2 = unit above barbell1
val barbell3 = unit above barbell2
val barbell4 = unit above barbell3
val barbell5 = unit above barbell4
```

Note pattern: each $barbell_n$ value is built by putting a barbell above the $barbell_{n - 1}$ value. How to **generalize** this pattern?

## Loops and Conditionals

Basically, we want to express the following:

- $barbell_n =$
    - $unit$ if $n = 1$,
    - $unit$ $above$ $barbell_{n - 1}$ otherwise.

## Conditional and Boolean Expressions

```scala
val unit = barbell(weight(15))
def barbells(n: Int) =
  if (n == 1) unit
  else unit above ???
```

- You can write **conditional expressions** using `if` and `else`;

- The result of a conditional depends on the `Boolean` value of the condition;

- Boolean literals: `true` and `false`;

- Boolean expressions can be combined with `||` (disjonction), `&&` (conjonction), `!` (negation) and the comparison operations (e.g. `==`);

    - `true || false == !false`.

## Recursive Methods

```scala
val unit = barbell(weight(15))
def barbells(n: Int): Image =
  if (n == 1) unit
  else unit above barbells(n - 1)
```

- The return type of **recursive** methods is mandatory.

## Exercise

- Define a method `circles` that takes a number `n` as parameter and returns an image showing `n` concentric circles of increasing sizes.

![](circles.png)

<!--
def circles(n: Int): Image =
  if (n == 1) Circle(25)
  else Circle(25 + 15 * n) on circles(n - 1)
-->

## Exercise

<!--
def triangle(size: Double): Image =
  Triangle(size, size) lineColor Color.magenta

def sierpinski(n: Int, size: Double): Image =
  if(n == 1) triangle(size) else {
    val smaller = sierpinski(n - 1, size/2)
    smaller above (smaller beside smaller)
  }
-->

# Syntax Summary

## Syntax Summary

### Definitions

- `val <name> = <expr>`

- `def <name>(<p1>, <p2>, …) = <expr>`

### Expressions

- `if (<expr>) <expr> else <expr>`

- `<name>(<expr>, <expr>, …)`

- `<expr>.<name>(<expr>, …)`

# Evaluation Model

## Evaluation Model

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

>   - Evaluation steps:
>
>     ```scala
>     fact(4)
>     if (4 <= 1) 1 else 4 * fact(4 - 1)
>     4 * fact(3)
>     4 * if (3 <= 1) 1 else 3 * fact(3 - 1)
>     4 * 3 * fact(2)
>     4 * 3 * if (2 <= 1) 1 else 2 * fact(2 - 1)
>     4 * 3 * 2 * fact(1)
>     4 * 3 * 2 * if (1 <= 1) 1 else 1 * fact(1 - 1)
>     4 * 3 * 2 * 1
>     12 * 2 * 1
>     24 * 1
>     24
>     ```

## Termination

Is the evaluation process guaranteed to terminate?

```scala
def loop: Nothing = loop
```

<!--
## Linear and Tail Recursion

TODO
-->

## String Interpolation

**String interpolation** is a mechanism to build strings from dynamic values:

```scala
def greet(name: String) = s"Hello, $name!"
```

- Prefix a string literal with `s`

- Within the string, use `$<name>` to insert a value

```scala
greet("World") // "Hello, World!"
```

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

## Making an Executable Program from a Source File (2)

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
- Move your `Main.scala` file into a `my-project/src/main/scala` directory
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

- code refactoring

## Eclipse and IntelliJ

### [Eclipse](http://www.eclipse.org)

- Install the [Eclipse Scala plugin](http://scala-ide.org/)

- Use the [sbt eclipse plugin](https://github.com/typesafehub/sbteclipse)

### [IntelliJ IDEA](http://www.jetbrains.com/idea/)

- Install the IntelliJ Scala plugin

- Use the [sbt idea plugin](https://github.com/mpeltonen/sbt-idea)

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

## Organizing your Code (2)

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

## Exercise

> If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
>
> Find the sum of all the multiples of 3 or 5 below 1000.

[Euler Problem #1](http://projecteuler.net/problem=1)

## Solution

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

## Modeling Complex Numbers

You want to design a program manipulating **complex numbers**

A complex number has a *real part* and an *imaginary part*, it can be expressed in the form *a + b **i***

Complex numbers can be added, substracted, multiplied, divided, etc.

## Modeling Complex Numbers (2)

To compute the product of two complex numbers, you could write the following functions:

```scala
def mulR(xR: Double, xI: Double, yR: Double, yI: Double) =
  xR * yR - xI * yI
def mulI(xR: Double, xI: Double, yR: Double, yI: Double) =
  xI * yR + xR * yI
```

And you could use them as follows:

```scala
val xR = 1; val xI = 2 // x = 1 + 2 i
val yR = 3; val yI = 4 // y = 3 + 4 i

val zR = mulR(xR, xI, yR, yI)
val zI = mulI(xR, xI, yR, yI)
```

> - Note how this design forces you to repeat things

> - Such a code would be very **error prone** and **hard to read and maintain**

## Abstract Data Types

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
```

>   - Referring to a complex number requires only one symbol
>
>     ```scala
>     val x = new Complex(1, 2)
>     val y = new Complex(3, 4)
>     ```
>
>   - Multiplying two complex numbers requires only one operation
>
>     ```scala
>     val z = x.mul(y)
>     ```

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

* Add a `pow(e: Int): Complex` method that raises a complex number to the power of `e`

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

    - (Note that it could also have implemented members)

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

- `ComplexRectangular` **extends** (or **subclasses**) `Complex`
- `Complex` is a **superclass** of `ComplexRectangular`
- `ComplexRectangular` **implements** the abstract members of `Complex`

## Implementing an Abstract Class (2)

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

## Parameters vs. Abstract Members

Consider this part of the `ComplexRectangular` definition:

```scala
class ComplexRectangular(a: Double, b: Double) extends Complex {
  val real = a
  val imag = b
}
```

  - The `real` member implementation just aliases the `a` parameter (the same applies to `imag` and `b`)

>   - You can declare `real` and `imag` members in the class constructor parameters list:
>
>     ```scala
>     class ComplexRectangular(val real: Double, val imag: Double) extends Complex
>     ```
>
>   - Note that `def` members can not be declared as parameters

## Abstract Members, Encapsulation and Modularity

- Abstract members achieve encapsulation *via* **data abstraction**

- Encapsulation allows the construction of **abstraction layers**

- It is a key principle to achieve **modularity**: the underlying implementation can change without affecting users

## Specialization

`Complex` is more **general** than `ComplexRectangular` and `ComplexPolar`

You could also say that `ComplexRectangular` and `ComplexPolar` are more **specialized** than `Complex`

When specializing a type, you can:

- Implement abstract members
- Add new members
- Redefine non-abstract members

A subclass **conforms** to its superclass, so it can be used everywhere the superclass is needed

## Adding a Member to a Base Class

```scala
class SemiGroup {
  def append(a: Int, b: Int) = a + b
}

class Monoid extends SemiGroup {
  def zero = 0
}

class Group extends Monoid {
  def inverse(a: Int) = -a
}
```

## Redefining a Member of a Base Class

```scala
class LoggingSemiGroup extends SemiGroup {
  override def append(a: Int, b: Int) = {
    println(s"Calling append($a, $b)")
    super.append(a, b)
  }
}
```

- Redefine a member using `override`
- You can still refer to the base implementation using `super`

## Members Visibility

You can reduce the visibility of the members of a class:

```scala
abstract class Foo {
  private def foo = 42
  protected def bar: Int
}
```

- `private` members are not visible outside of the class definition

- `protected` members are not visible outside of the class definition except for subclasses

## Closing a Class Hierarchy

You can forbid the specialization of a class or of its members:

```scala
class Foo {
  final def foo = 42
}

final class Bar extends Foo
```

- `final` members can not be overriden

- `final` classes can not be extended

## Overloading

Several members with the same name can coexist, as long their type signature is different:

```scala
def add(that: Complex): Complex = …
def add(real: Double): Complex = add(new Complex(real, 0)
```

## Standard Class Hierarchy

![Scala Class Hierarchy](ScalaClassHierarchy.png)

## Recursive Types

<!-- TODO Motivating problem -->

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

## Exercise

Implement an abstract data type representing a sequence of integers of arbitrary size:

```scala
abstract class IntList {
  def add(n: Int): IntList
  def concat(ns: IntList): IntList
}
```

Override the `toString` method to return `"Nil"` for the empty sequence, and `"1 :: 2 :: Nil"` for the sequence {1, 2}

> - Hint: consider two special cases of an `IntList`: an empty list (`Nil`) and a list constructor (`Cons`) containing a head element and a tail list


# Higher-Order Functions and Function Literals

## Abstract Over Computations

### Exercise

* Add to `IntList` a method `sum: Int` that returns the sum of the elements of the list

* Then add a method `product: Int` that returns the product of the elements of the list

> * Note the **similarities** between `sum` and `product`

> * Can you write a more general function that could be reused to define `sum` and `product`?

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
        def sum = fold(0, (n, s) => s + n)
        def product = fold(1, (n, p) => p * n)
        ```

* Add a `foreach(f: Int => Unit): Unit` member, that applies the function `f` to each element of the list

* Add a `filter(p: Int => Boolean): IntList` member that returns a list containing all the elements satisfying `p`

## Function Literals

In some cases, you’d like to avoid to repeat the parameter name of a literal function:

```scala
val inc: Int => Int = x => x + 1
```

You can use an underscore as a placeholder for the function parameter:

```scala
val inc: Int => Int = _ + 1
```

If the function takes several parameters, you can use as many underscores as parameters:

```scala
def sum = fold(0, _ + _)
def product = fold(1, _ * _)
```

## Currying and Partial Application

```scala
def add(a: Int, b: Int) = a + b

add(3, 4) // 7
```

An equivalent computation could be the following:

```scala
def add(a: Int) = (b: Int) => a + b

add(3)(4) // 7
```

The first version takes two numbers as parameters and returns a number

The second version takes one number as parameter and returns a function that takes the second number and returns their sum

We say that the second version is a **curried** function

## Currying and Partial Application (2)

Curried functions can be partially applied:

```scala
scala> val inc = add(1)
inc: Int => Int = <function1>

scala> inc(5)
res0: Int = 6
```

Alternatively, you can write the curried version using several **parameters lists**:

```scala
def add(a: Int)(b: Int) = a + b
```


# Composition Mechanisms

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

## Tuples

Classes can combine several values together

But sometimes you just want to aggregate values without defining additional methods

In such cases, defining an extra class would be cumbersome, you can use **tuples** instead:

```scala
def euclideanDiv(dividend: Int, divisor: Int): (Int, Int) = {
  val quotient = dividend / divisor
  val remainder = dividend % divisor
  (quotient, remainder)
}
```

> - The type `(T1, …, Tn)` is a tuple type of `n` elements which i^th^ element has type `Ti`
> - The value `(t1, …, tn)` is a tuple value of `n` elements

## Tuples (2)

```scala
val qr = euclideanDiv(42, 10)
println(qr._1)
println(qr._2)
```

> - Get the i^th^ element of a tuple with the member `_i`

```scala
val (q, r) = euclideanDiv(42, 10)
```

> - Or use a **tuple pattern** to *deconstruct* a tuple

## Components

You saw how to implement a set of features in a single component using classes

Is it possible to build a larger system by combining small components together?

For instance, given the following class definitions:

```scala
class Adding {
  def add(a: Int, b: Int) = a + b
}
```

```scala
class Multiplying {
  def mul(a: Int, b: Int) = a * b
}
```

How to build calculator from these two components?

## Traits

**Traits** can encapsulate members and can be mixed together

```scala
trait Adding {
  def add(a: Int, b: Int) = a + b
}

trait Multiplying {
  def mul(a: Int, b: Int) = a * b
}

trait Calculator extends Adding with Multiplying
```

> - A trait definition is like a class definition, except that **traits can not have constructor parameters**
> - Traits can have abstract members
> - Traits can be mixed in another trait using `extends` and `with`

## Traits and Dynamic Dispatch

```scala
trait Simple {
  def value = 7
}

trait Double extends Simple {
  override def value = super.value * 2
}

trait Triple extends Simple {
  override def value = super.value * 3
}
```

```scala
val mixin = new Simple with Double with Triple
println(mixin.value) // What is printed?
```

Which implementation of the `value` member is called?

## Traits and Dynamic Dispatch (2)

![Traits Linearization](Traits.png)


# Type Polymorphism

## Type Polymorphism

Until now, you saw only how to abstract over values

It is also possible to abstract over **types**

## Exercise

<!-- FIXME Use `identity(n: Int): Int`, `identity(b: Boolean): Boolean` as an example? -->

* Add a method `forAll(p: Int => Boolean): Boolean` to `IntList`, that tests if the predicate `p` holds for all the elements of the list

* Then add a method `hasEvenSize: Boolean` that tests if the list has an even size

* Generalize `forAll` and `hasEvenSize` by adding a method `foldBool(z: Boolean, op: (Int, Boolean) => Boolean): Boolean`

> * Note that the implementations of `fold` and `foldBool` are **exactly the same**.

> * Is it possible to write only one `fold` function that would work with both `Int` and `Boolean`?

## Polymorphic Functions

Look at the type signatures of `fold` and `foldBool`:

```scala
(Int,     (Int, Int)     => Int)     => Int
(Boolean, (Int, Boolean) => Boolean) => Boolean
```

You could add a `foldString` with the following type signature:

```scala
(String,  (Int, String)  => String)  => String
```

It always follows this pattern:

```scala
(A, (Int, A) => A) => A
```

## Polymorphic Functions (2)

Functions can have **type parameters**:

```scala
def fold[A](z: A, op: (Int, A) => A): A
```

- `A` is a **type parameter** (**universally** quantified), `fold` is a **polymorphic function**

You can then call `fold` as follows:

```scala
def sum = fold[Int](0, (n, s) => s + n)
def forAll(p: Int => Boolean) = fold[Boolean](true, (n, b) => b && p(n))
```

>   - Note that if you use the following signature you can help the type inference mechanism and omit the applied type in most cases:
>
>     ```scala
>     def fold[A](z: A)(op: (Int, A) => A): A
>     ```
>
>     ```scala
>     def sum = fold(0)((n, s) => s + n)
>     ```

## Exercise

* Make `fold` polymorphic

## Exercise

* Implement an abstract data type representing a list of `String` elements:

```scala
abstract class StringList {
  def add(str: String): StringList
  def concat(ss: StringList): StringList
}
```

> - Note the strong similarities with `IntList`
> - You want to abstract over the type of the elements of the list

## Type Constructors

Types can have type parameters:

```scala
abstract class List[A] {
  def add(element: A): List[A]
  def concat(as: List[A]): List[A]
}
```

> - `List` is a **type constructor**: it takes a type as parameter and yields another type
>     - E.g. `List[Int]`, `List[String]`, etc.

## Exercise

* Write a polymorphic `List[A]` data type:

```scala
abstract class List[A] {
  def add(element: A): List[A]
  def concat(as: List[A]): List[A]
  def fold[B](z: B)(op: (A, B) => B): B
}
```

## Type Quantification

Subtyping and universal types are two different generalization mechanisms. Can you mix them together?

Consider the following class hierarchy:

```scala
trait Animal {
  def fitness: Int
}

trait Reptile extends Animal

trait Mammal extends Animal

trait Zebra extends Mammal {
  def zebraCount: Int
}

trait Giraffe extends Mammal
```

You want to write a function `selection`, that takes two animals as parameters and returns the one with the highest `fitness` value. What will its type signature be?

## Type Quantification (2)

A possible solution using subtyping is the following:

```scala
def selection(a1: Animal, a2: Animal) =
  if (a1.fitness > a2.fitness) a1 else a2
```

What is the problem with this solution?

>   - The return type is `Animal`: call it with two `Zebra`s and you get only an `Animal`
>       - You loose the ability to access further its `zebraCount` member

## Type Quantification (3)

We could try to solve this issue using a universal type:

```scala
def selection[A](a1: A, a2: A): A =
  if (a1.fitness > a2.fitness) a1 else a2
```

But this solution does not compile: we can not access the `fitness` member of `a1` and `a2` because `A` is not constrained to be a subtype of `Animal`

## Upper and Lower Bounds

We can write a better solution using a **bounded quantification**:

```scala
def selection[A <: Animal](a1: A, a2: A): A =
   if (a1.fitness > a2.fitness) a1 else a2
```

- `A <: Animal` means “for all type `A` that is a subtype of `Animal`”, and we say that `Animal` is the **upper bound** of `A`

Similarly, you can constraint a type parameter `A` to have a **lower bound** `B`:

 - `A >: B`

## Variance

Consider the following type modeling a zoo run containing an animal:

```scala
abstract class Run[A] {
  def get: A // Get the animal that lives in this run
}
```

Mammal well being is a serious subject, the following function checks that a given run has the appropriate size regarding to the animal that lives in (provided you have methods in `Run[A]` and `Mammal` that give you their size):

```scala
def isLargeEnough(run: Run[Mammal]): Boolean = …
```

What happens if you pass it a `Run[Zebra]` as parameter?

## Variance (2)

```scala
scala> isLargeEnough(zebraRun)
<console>:14: error: type mismatch;
 found   : Run[Zebra]
 required: Run[Mammal]
```

A `Run[Zebra]` is not a subtype of `Run[Mammal]`, in fact they are just two different types without any subtyping relation. Could it be otherwise? Should it be otherwise?

> - Intuitively, a run with a zebra is just a special case of a run with a mammal
> - `isLargeEnough` can only use the `get` member of its `Run[Mammal]` parameter, and expects to get a `Mammal` value
> - A `Run[Zebra]` value has a `get` member that returns a `Zebra`, which is a `Mammal`
> - It seems that `Run[Zebra]` should be a subtype of `Run[Mammal]`

## Covariance

More generally, `Run[A] <: Run[B]` if `A <: B`

We say that `A` is **covariant** in `Run[A]` and we can tell it to the compiler as follows:

```scala
abstract class Run[+A] {
  def get: A
}
```

Now the compiler accepts that you pass a `Run[Zebra]` where a `Run[Mammal]` is expected

## Variance (3)

Consider the following type modeling a veterinary able to treat animals:

```scala
abstract class Vet[A] {
  def treat(a: A)
}
```

And the following function treating all the mammals of the zoo using a veterinary passed as parameter:

```scala
def treatMammals(vet: Vet[Mammal]) { … }
```

What happens if you pass it a `Vet[Animal]` as parameter?

## Variance (4)

```scala
scala> treatMammals(animalVet)
<console>:14: error: type mismatch;
 found   : Vet[Animal]
 required: Vet[Mammal]
```

A `Vet[Animal]` is not a subtype of `Vet[Mammal]`, they are just two different types. Should it be otherwise?

> - Intuitively, a veterinary that can treat animals can treat mammals, because a mammal is just a special case of an animal
> - `treatMammals` can only use the `treat` member of its `Vet[Mammal]` parameter, and can pass it any `Mammal` value
> - A `Vet[Animal]` has a `treat` member that takes an `Animal` as parameter, so it can take a `Mammal` as well, because `Mammal` is a subtype of `Animal`
> - It seems that `Vet[Animal]` should be a subtype of `Vet[Mammal]`

## Contravariance

More generally, `Vet[A] <: Vet[B]` if `A >: B`

We say that `A` is **contravariant** in `Run[A]` and we can tell it to the compiler as follows:

```scala
abstract class Vet[-A] {
  def treat(a: A)
}
```

Now the compiler accepts that you pass a `Vet[Animal]` where a `Vet[Mammal]` is expected

## Variance (5)

For a type constructor `F[X]`, variance annotations allow you to define the subtyping relation of the types `F[A]` and `F[B]` according to the subtyping relation of the types `A` and `B`

By default, type parameters are **invariant**

## Exercise

* Make the `List` class covariant


# Object Orientation

## Every Value is an Object

Scala is a pure object-oriented language

Every value is an object

The type of each value is a class

## Functions are Objects

### Definition

```scala
trait Function[-T, +R] {
  def apply(parameter: T): R
}
```

The `Int => String` type is a shorthand for the `Function[Int, String]` type

The `(x: Int) => x + 1` expression is a shorthand for the following expression:

```scala
new Function[Int, Int] {
  def apply(x: Int) = x + 1
}
```

## Functions are Objects (2)

### Application

`f(t1, t2, …)` is a shorthand for `f.apply(t1, t2, …)`

- Note that the underlying value does not need to extend `Function`, it only needs to have an `apply` method

## Infix Notation for Methods

Methods with one parameter can be used like **infix operators**:

```scala
xs add 3 // Equivalent to “xs.add(3)”
```

This is especially convenient when methods have symbolic names:

```scala
1 + 2 // Equivalent to “1.+(2)”
```

> - How is the expression `1 + 2 * 3` parenthesized?
>     - `((1 + 2) * 3)` or `(1 + (2 * 3))`?

## Operators Precedence Rules

Operators **precedence** depends on their **first character** and is given by the following list (highest first):

- all special characters (excepted those below)
- `*` `/` `%`
- `+` `-`
- `:`
- `=` `!`
- `<` `>`
- `&`
- `^`
- `|`
- all letters

## Operators Associativity Rules

Consider the following expression building the sequence $\{1, 2\}$:

```scala
(new Nil[Int]) add 2 add 1
```

Because lists are constructed by “pushing” elements in front, the result of this expression is a list whose **first** element is `1`

However, in the above expression this element appears in **last** position, which is not intuitive

You would like to write an expression looking like the following, instead:

```scala
1 add 2 add (new Nil[Int])
```

## Operators Associativity Rules (2)

Operators **associativity** depends on their **last character**

By default they are **left-associative**: `empty[Int] add 2 add 1` is parenthesized as `(empty[Int] add 2) add 1`

If the operator name ends with character `:` or `=`, it becomes **right-associative**

If the `add` member was renamed to `::` you could write the following expression:

```scala
1 :: 2 :: (new Nil[Int])
```

This expression would be parenthesized as `1 :: (2 :: empty[Int])`

## Symbolic vs Alphanumeric Names

Consider the two equivalent programs:

```scala
(new Nil[Int]) add 2 add 1
```

```scala
1 :: 2 :: (new Nil[Int])
```

Symbolic names can make the code **more readable** by differentiating names referring to values and names referring to operators

Symbolic names can make the code **less readable** because they may be less meaningful than alphanumeric names

## Singleton Objects

Creating an object requires calling a constructor prefixed with the `new` keyword

<!-- TODO More on the motivation -->

Alternatively, you can define **singleton objects**:

```scala
object MyObject {
  val foo = 42
}
```

`MyObject` is a value of type `MyObject.type` that can be used as follows:

```scala
println(MyObject.foo)
```

## Top-Level Definitions

Singleton objects and classes (or traits) are the only allowed top-level definitions

Any other definition (`val`, `def`) must be nested within a top-level definition

## Companion Objects

An object definition with the same name as a class and declared in the same file is called a **companion object** for this class

A class and its companion object can access each other’s private members

Companion objects are a good place to define functions related to their companion class (e.g. factory methods)

## Exercise

* Add the alias `++` for the `concat` member of `List`

* Add the alias `::` for the `add` member of `List`

* An empty list of `Int` and an empty list of `String` could be represented by a same value. What would be its type? Define such a value as a singleton object, call it `Nil`


# Algebraic Data Types

## Structural Identity

Consider the following REPL session:

```scala
scala> import julienrf.course._
import julienrf.course._

scala> 1 :: 2 :: Nil == 1 :: 2 :: Nil
res0: Boolean = false

scala> 1 == 1
res1: Boolean = true
```

Why does the first expression return `false`?

## `==`

`Any` is the superclass of all classes

It has at least two members that you should be aware of:

```scala
final def == (that: Any): Boolean
def equals(that: Any): Boolean
```

First, note that these members exist in this class mostly for compatibility reasons with the Java language

The `==` member allows you to compare any value with another value. It essentially delegates to `equals`

The default implementation of `equals` for classes compares the **references** `this` and `that`

## A Type as a Space of Possible Values

Until now, we thought of types as a **programmation interface**: a list of available members for a given type name

E.g. type `Complex` has a `real` member

Alternatively, you can think of types as **sets of possible values**

- type `Boolean` has two possible values: `true` and `false`
- type `Int` has 2^32^ possible values
- a type `State` with three possible values: `Sleeping`, `Eating` and `Working`

Then, you can **combine** these sets to create new sets of values

## Product Types

What are the possible values of a type aggregating a `Boolean` value **and** a `State` value?

`Boolean` `State`
--------- ----------
`true`    `Sleeping`
`true`    `Eating`
`true`    `Working`
`false`   `Sleeping`
`false`   `Eating`
`false`   `Working`

The number of values is equal to the **product** of the number of values of the `Boolean` and the `State` types

## Sum Types

What are the possible values of a type containing either a `Boolean` value **or** a `State` value?

`Boolean` `State`
--------- -------
`true`
`false`
          `Sleeping`
          `Eating`
          `Working`

The number of values is equal to the **sum** of the number of values of the `Boolean` and the `State` types

## Algebraic Data Types

**Algebraic data types** are defined as *sums* or *products* of other types

A type is essentially a set of possible values

In that case, it makes sense to assume that two instances of a type are the same if they represent the same value in the set of possible values

## Defining `List` as an Algebraic Data Type

A list is either:

- an empty list, `Nil` ;

- a list constructor, `Cons(head, tail)`, containing a `head` element and a `tail` list (which is a `List` itself)

## Encoding Algebraic Data Types

**case classes** turn regular classes into **product types**:

```scala
case class Person(isMarried: Boolean, state: State)
```

- Constructor parameters become members (as if they were prefixed with `val`)
- An `equals` implementation is generated, performing structural comparison
    - `new Person(true, Working) == new Person(true, Working)`
- A companion object with an `apply` member matching the class constructor parameters is automatically generated
    - You can omit the `new` keyword to create an instance: `Person(true, Working)`
- A `copy` member is also automatically generated
    - `Person(true, Working).copy(state = Sleeping)`

## Encoding Algebraic Data Types (2)

**sealed classes** and **inheritance** encode **sum types**:

```scala
sealed abstract class Person
case class SuperHero(isSavingTheWorld: Boolean) extends Person
case class Human(state: State) extends Person
```

- A `case class` can not be extended
- A `sealed class` can not be extended, except if the subclass is defined in the same source file
- A `Person` can either be a `SuperHero` or a `Human`, but nothing else

The `State` type could be implemented as follows:

```scala
sealed abstract class State
case object Sleeping extends State
case object Eating extends State
case object Working extends State
```

## Pattern Matching

**Pattern matching** can be used to deconstruct algebraic data types:

```scala
def isBusy(person: Person) = person match {
  case SuperHero(isSavingTheWorld) =>
    isSavingTheWorld
  case Human(state) =>
    state == Working
}
```

## Exercise

* Make `List` an algebraic data type

## Pattern Matching vs Fold

Consider the following programs:

```scala
def sum(xs: List[Int]) = xs match {
  case Nil => 0
  case Cons(x, xs) => x + sum(xs)
}
```

```scala
def sum(xs: List[Int]) =
  xs.fold(0)((x, s) => x + s)
```

- `fold` was your poor man’s pattern matching mechanism

## Algebraic Data Types and Data Abstraction

Should you use algebraic data types or regular classes?

- Algebraic data types are **closed types** (they can not be extended), but this characteristic is what makes it easier to add new operations on a type hierarchy

- Classes are **open types** (they can be extended), but you can not add a new operation on a class hierarchy without changing the whole hierarchy


# Handling Failure

## Motivating Problem

* Try to add a `head: A` member to your `List[A]` type, that returns the first element of a list

> - What should you return in case of the empty list?

## `Option`

A list may have a head element, but that is not always the case

The standard library defines the type `Option[A]` to model optional values

An `Option[A]` value can either be:

- `Some(a)`

- `None`

## `Option` (2)

```scala
def safeDiv(a: Int, b: Int): Option[Int] =
  if (b == 0) None
  else Some(a / b)
```

```scala
val maybeQ = safeDiv(42, 10)

maybeQ match {
  case Some(q) => println(q)
  case None => println("Division by zero")
}

println(q getOrElse "Division by zero")
```

## Exercise

  * Implement the following methods:

    ```scala
    def headOption: Option[A]
    def tailOption: Option[List[A]]
    ```

## Common Patterns With Optional Values

Use `map` to transform a successful value into another successful value, ignoring the `None` case:

```scala
def inc(maybeN: Option[Int]): Option[Int] =
  maybeN.map(n => n + 1)

def toString(maybeN: Option[Int]): Option[String] =
  maybeN.map(_.toString)
```

## Common Patterns With Optional Values (2)

Use `filter` to turn a successful value into a failure if it does not satisfy a given predicate:

```scala
def even(maybeN: Option[Int]): Option[Int] =
  maybeN.filter(n => n % 2 == 0)
```

## Common Patterns With Optional Values (3)

Use `flatMap` to transform a successful value into an optional value:

```scala
def inverse(maybeN: Option[Int]): Option[Int] =
  maybeN.flatMap(n => safeDiv(1, n))
```

## Sequencing Computations Manipulating Optional Values

`flatMap` and `map` are used to apply sequenced computations to optional values:

```scala
def foo(maybeN: Option[Int]): Option[Int] =
  maybeN.flatMap { n =>
    safeDiv(1, n).flatMap { q =>
      intSqrt(q).map { q2 =>
        q2 + 1
      }
    }
  }
```

(provided `intSqrt` has type signature `Int => Option[Int]` and returns a successful square root only of its parameter is a perfect square)

(We will see a more expressive way of expressing such computations, soon)

## `Either`

When using a optional value, getting `None` gives you no clue of why there is no value: all you know is that you have no value

The `Either[A, B]` type can be useful to handle failures while keeping track of a reason for the failure

An `Either[A, B]` value can either be:

- `Left(a)`

- `Right(b)`

Conventionnaly the left case is used to store the failure information

## `Either` (2)

```scala
def safeDiv(a: Int, b: Int): Either[String, Int] =
  if (b == 0) Left("Division by zero")
  else Right(a / b)
```

```scala
val maybeQ = safeDiv(42, 10)

maybeQ match {
  case Right(q) => println(q)
  case Left(error) => println(error)
}
```

<!-- TODO

## Try, `try`/`catch`/`throw`

-->

# Standard Collections

## Standard Collections

The Scala standard library provides several types making it easier to deal with collections of elements

This section gives a slight overview of the standard collections. For more details see the [API documentation](http://scala-lang.org/api)

## `Traversable`

The most general one is `Traversable[A]`, it provides methods to iterate on the elements of a collection, to transform a collection, to filter it, and a lot more:

Method           Description
----------       -------------
`xs.foreach(f)`  Applies the function `f` to every element of `xs`
`xs ++ ys`       The concatenation of the elements of `xs` and `ys`
`xs.size`        The number of elements in `xs`
`xs.map(f)`      A collection obtained from applying `f` to every element of `xs`
`xs.flatMap(f)`  A collection obtained from applying `f` to every element of `xs` and concatenating the results
`xs.filter(p)`   A collection obtained from filtering elements of `xs` that satisfy the predicate `p`
`xs.take(n)`     A collection containing the `n` first elements of `xs`
`xs.find(p)`     An optional value containing the first element of `xs` that satisfies `p`
`xs.headOption`  An optional value containing the first element of `xs`
`xs.tailOption`  An optional value containing the tail of `xs`

## `Iterable`

`Iterable[A]` extends `Traversable[A]`, here are some of its new members:

Method             Description
-----------        -------------
`xs.iterator`      An `Iterator[A]` over the elements of `xs`
`xs.grouped(n)`    An iterator that yields fixed-size chunks of `xs`
`xs zip ys`        An iterable of pairs of corresponding elements of `xs` and `ys`
`xs.zipWithIndex`  An iterable of pairs of elements of `xs` with their indices

## `Seq`

`Seq[A]` is an `Iterable[A]` which order of elements is kept. It adds the following members:

Method               Description
----------           ---------------
`x +: xs`            A collection with the elements of `xs` prepended with `x`
`xs :+ x`            A collection with the elements of `xs` followed by `x`
`xs.get(n)`          An optional value containing the `n`^th^ element of `xs` (0 indicates the first element)
`xs.reverse`         A collection with the elements of `xs` in reverse order
`xs.updated(n, x)`   A copy of `xs` which `n`^th^ element has been replaced by `x`
`xs.sorted`          A collection with the elements of `xs` sorted

## `List` and `Vector`

`List[A]` and `Vector[A]` are two implementations of `Seq[A]` with different performance characteristics:

- `List[A]` has more efficient `head` and `tail` implementations

- `Vector[A]` has more efficient `get` and `size` implementations

- If you want to do random access, you should use `Vector[A]`

```scala
val xs = 1 :: 2 :: 3 :: Nil // List
val ys = Vector(1, 2, 3)
```

## `Range`

`Range` is a useful implementation of `Seq[Int]` that efficiently represents a range of integer values

The simplest way to generate a range is to use `to` and `until` methods of numeric values:

```scala
(1 to 3).foreach(println) // prints “1”, “2”, “3”
(0 until 3).map(_ * 2).foreach(println) // prints “0”, “2”, “4”
```

## `Set`

A `Set[A]` is an `Iterable[A]` that contains no duplicate elements. It adds the following members:

Method               Description
-----------          ----------------
`xs + x`             A set containing the elements of `xs` and `x`
`xs - x`             A set containing the elements of `xs` without `x`
`xs.contains(x)`     Tests if `x` is contained in `xs`
`xs & ys`            Intersection of `xs` and `ys`
`xs | ys`            Union of `xs` and `ys`
`xs.subsetOf(ys)`    Tests if `xs` is a subset of `ys`

```scala
val xs = Set(1, 2, 3)
```

## `Map`

A `Map[A, B]` is an `Iterable[(A, B)]` that contains `B` values indexed by `A` values. It adds the following members:

Method               Description
-------------        ---------------
`xs.get(k)`          An optional value containing the value associated with `k`
`xs + (k -> x)`      Adds a new value `x` associated with `k`
`xs - k`             Removes the element associated with `k`
`xs.keys`            An iterable of the keys of `xs`

```scala
val xs = Map("foo" -> 42, "bar" -> 10, "baz" -> 0)
```

## Exercise

* Add the following members to your `List[A]` data type:

```scala
def size: Int
def take(n: Int): List[A]
def drop(n: Int): List[A]
def map[B](f: A => B): List[B]
def flatMap[B](f: A => List[B]): List[B]
```

* Write a one-line solution to the Euler problem #1 using standard collections

* Write a function `isPrime(n: Int): Boolean` that tests if `n` is a prime number


# `for` Notation

## Exercise

* Write a function `primes` that takes a parameter `n` and returns all pairs of integers `i` and `j` (with `1 <= j < i < n`) such that `i + j` is prime

## Solution

```scala
def primes(n: Int): Seq[(Int, Int)] =
  (1 until n).flatMap { i =>
    (1 until i).map { j =>
      (i, j)
    }.filter(p => isPrime(p._1, p._2))
  }
```

> - We nest `flatMap`, `map` and `filter` calls
>
> - Do you remember the same pattern with optional values?

## Sequencing Computations

Actually, `flatMap` and `map` functions are very useful to sequence computations within a given *context* (optional values, collections, and much more)

They are so common that Scala supports a more convenient syntax, the **`for` notation**, that desugars to `flatMap`, `map` and `filter` calls

For instance `primes` can be written as follows using the `for` notation:

```scala
def primes(n: Int): Seq[(Int, Int)] =
  for {
    i <- 1 until n
    j <- 1 until i
    if isPrime(i + j)
  } yield (i, j)
```

## Translation of `for`

```scala
for (x <- xs) yield x + 1
```

is translated to:

```scala
xs.map(x => x + 1)
```

## Translation of `for` (2)

```scala
for (x <- xs; y <- ys) yield (x, y)
```

is translated to:

```scala
xs.flatMap(x => for (y <- ys) yield (x, y))
```

## Translation of `for` (3)

```scala
for (x <- xs if x % 2 == 0) yield x + 1
```

is essentially translated to:

```scala
for (x <- xs.filter(x => x % 2 == 0)) yield x + 1
```


# Imperative Programming

## Motivating Problem: Random Number Generator

Try to implement the following random number generator:

```scala
class RNG {
  def next(): Int = ???
}
```

> Hint: a simple way to generate a sequence of pseudorandom values is to use the following recurrence relation:
>
> x~n+1~ = 22695477 x~n~ + 1

Example of use:

```scala
val rng = new RNG
println(rng.next())
println(rng.next())
```

> - The `next` member is impossible to implement!

## Motivating Problem: Random Number Generator

By changing the `next` signature, a possible implementation would be the following:

```scala
class RNG {
  def next(x: Int) = 22695477 * x + 1
}
```

But users would be required to *remember* each value to get the next one:

```scala
val rng = new RNG
val x1 = rng.next(1)
println(x1)
val x2 = rng.next(x1)
println(x2)
```

## Assignment

A `var` definition associates a value with a name, like `val` does, but you can **assign** later a new value to this name:

```scala
var x = 10
println(x) // “10”
x = x + 10
println(x) // “20”
```

## Stateful Objects

`RNG` can be implemented as follows using a `var` member:

```scala
class RNG {
  private var x = 1
  def next() = {
    x = 22695477 * x + 1
    x
  }
}
```

- The `x` member stores the object’s **state**

## Imperative Loops

```scala
def fact(n: Int) = {
  var result = 1
  var i = 2
  while (i <= n) {
    result = result * i
    i = i + 1
  }
  result
}
```

## Benefits of Assignment

- **Stateful objects** help maintaining code modularity

    - The stateful version of `RNG` does not require users to explicitly manipulate objects state (by passing an additional parameter)

## Identity and State

```scala
val rng1 = new RNG
val rng2 = new RNG
```

Are `rng1` and `rng2` the same objects? At first glance they seem to be equal

```scala
println(rng1.next()) // “22695478”
println(rng1.next()) // “-2138921681”
println(rng2.next()) // “22695478”
```

`rng1` and `rng2` have distinct **effects**, they are not equals in a sense that we can not substitute one by the other

The introduction of assignment leads to the loss of **referential transparency** and makes reasonning about programs drastically more difficult

## Stateful Objects, Sharing and Copying

```scala
val rng1 = new RNG
val rng2 = rng1
```

Giving two different names to the *same* object can be confusing: applying a method on `rng1` will have an effect on `rng2`!

Stateful objects force you to distinguish between the intents of **sharing** and **copying**

This problem does not exist with **immutable objects**

## Pure Functions and Side-Effects

Without assignment, the following declaration has only one possible implementation:

```scala
def mystery[A](a: A): A
```

>   - The identity function:
>
>     ```scala
>     def mystery[A](a: A): A = a
>     ```
>
>   - But with assignment, the following becomes possible:
>
>     ```scala
>     def mystery[A](a: A): A = {
>       destroyTheWorld()
>       a
>     }
>     ```
>
>   - Without assignment the type signature of a computation tells a lot about its behavior

## Immutability When Possible

> In addition to raising complications about computational models, programs written in imperative style are susceptible to bugs that cannot occur in functional programs.

Harold Abelson *et. al.* *Structure and Interpretation of Computer Programs*. MIT Press 1993

> Classes should be immutable unless there's a very good reason to make them mutable....If a class cannot be made immutable, limit its mutability as much as possible.

Joshua Bloch. *Effective Java*. Addison Wesley 2008


# Type Classes and Implicit Parameters

## Motivating Problem

Remember the `sum` method of `IntList`?

```scala
def sumInts(xs: List[Int]): Int = xs.fold(0)(_ + _)
```

What if we want to compute the sum of a list of complex numbers?

```scala
def sumComplexes(zs: List[Complex]): Complex =
  zs.fold(new Complex(0, 0))(_ add _)
```

- Can we abstract over the similarities of `sumInts` and `sumComplexes`?

## Motivating Problem (2)

```scala
trait Sumable[A <: Sumable[A]] {
  def + (that: A): A
}

def sum[A <: Sumable[A]](as: List[A], zero: A): A =
  as.fold(zero)(_ + _)
```

We can change the implementation of `Complex` to extend `Sumable[Complex]`:

```scala
trait Complex extends Sumable[Complex] {
  def + (that: Complex) = add(that)
  …
}
```

```scala
val zs = List(new Complex(1, 2), new Complex(3, 4))
sum(zs, new Complex(0, 0))
```

## Motivating Problem (3)

- We can not change the implementation of `Int` to make it extend `Sumable[Int]`

- Anyway, this `Sumable` trait captures only a part of the problem: we still need to manually supply the identity element corresponding to each type

## (Almost) Type Classes

```scala
trait Sumable[A] {
  def zero: A
  def append(a1: A, a2: A): A
}
```

```scala
def sum[A](as: List[A], A: Sumable[A]) =
  as.fold(A.zero)(A.append)
```

## Retroactive Extension

```scala
val sumableInt = new Sumable[Int] {
  val zero = 0
  def append(a1: Int, a2: Int) = a1 + a2
}
```

```scala
val xs = List(1, 2, 3, 4)
sum(xs, sumableInt)
```

## Retroactive Extension (2)

```scala
val sumableComplex = new Sumable[Complex] {
  val zero = new Complex(0, 0)
  def append(a1: Complex, a2: Complex) = a1.add(a2)
}
```

```scala
val zs = List(new Complex(1, 2), new Complex(3, 4))
sum(zs, sumableComplex)
```

## (Almost) Type Classes (2)

- The `Sumable[A]` trait captures everything we need to make a sum of a list of `A` (the identity element and the binary operation)

- However, we need to explicitly supply the instance corresponding to each type

    - To compute a sum of `Int` we explicitly pass `sumableInt`

    - To compute a sum of `Complex` we explicitly pass `sumableComplex`

## Implicit Parameters

```scala
def sum[A](as: List[A])(implicit A: Sumable[A]) =
  as.fold(A.zero)(A.append)
```

`sum` takes an **implicit parameter** of type `Sumable[A]`

If you define `sumableInt` and `sumableComplex` as **implicit values** you can omit to supply them when calling `sum`:

```scala
implicit val sumableInt = …
implicit val sumableComplex = …
```

```scala
sum(xs)
sum(zs)
```

## Implicit Parameters (2)

If you call a method without supplying its implicit parameters, the compiler tries to resolve them in the **implicit scope**

The implicit scope is basically built using (by order of priority, highest first):

- implicit values of the current lexical scope or outer scopes,

- explicitly imported implicit values (e.g. `import path.to.some.Implicits._`),

- the implicit values of companion objects of the implicit parameter’s type

## Context Bounds

```scala
def sum[A : Sumable](as: List[A]) = {
  val A = implicitly[Sumable[A]]
  xs.fold(A.zero)(A.append)
}
```

- `A : F` expands to an implicit parameter of type `F[A]`

- We say that `F` is a **context bound** for `A`

- You can retrieve an implicit parameter using the `implicitly` helper

- Sometimes the context bound notation is shorter than using an implicit parameter list


# Testing

## Testing

Testing is a practical way to check that your code does not something else than what it is expected to do

## Property-Based Testing

Property-based testing allows you to express general properties on your code and to check them an arbitrarily generated data

## scalacheck


# Named Parameters, Default Parameters and Repeated Parameters

## Named Parameters

```scala
Range(1, 10, 2)
```

It is difficult to figure out what the above program does: what is the meaning of these numbers?

Using **named parameters** can improve readability:

```scala
Range(start = 1, end = 10, step = 2)
```

## Default Parameters

TODO

## Repeated Parameters

TODO


# Laziness

## Lazy Vals

## By-Name Parameters

Remember the evaluation strategy for parameters?


# Type Members

## `type`

## Type Projection

## Path-Dependent Types

## Summary of Scala Abstraction Mechanisms

+--------+-------------------------+-----------------------+
|        | Parameters              | Members               |
+========+=========================+=======================+
| Values | ```scala                | ```scala              |
|        | def f(x: Int): Int =    | def f(x: Int): Int    |
|        |   x + 1                 | ```                   |
|        | ```                     |                       |
|        |                         | ```scala              |
|        |                         | val x: Int            |
|        |                         | ```                   |
+--------+-------------------------+-----------------------+
| Types  | ```scala                | ```scala              |
|        | def g[A](a: A): A = a   | type List             |
|        | ```                     | ```                   |
|        |                         |                       |
|        | ```scala                |                       |
|        | trait Ordering[A] { … } |                       |
|        | ```                     |                       |
+--------+-------------------------+-----------------------+


# Extractors


# Recommanded Lectures and References

## Recommanded Lectures and References

- *[Programming in Scala](http://www.artima.com/shop/programming_in_scala_2ed)*. Martin Odersky, Lex Spoon and Bill Venners. Artima 2010,

- *[Functional Programming Principles in Scala](https://www.coursera.org/course/progfun)*. Martin Odersky,

- *[Structure and Interpretation of Computer Programs](http://mitpress.mit.edu/books/structure-and-interpretation-computer-programs)*. Harold Abelson and Gerald Jay Sussman, with Julie Sussman. MIT Press 1996,

- *[Scala in Depth](http://www.manning.com/suereth/)*. Joshua D. Suereth. Manning 2012,

- *[Functional Programming in Scala](http://www.manning.com/bjarnason/)*. Paul Chiusano and Rúnar Bjarnason. Manning 2013.

## Recommanded Lectures and References (2)

- *[Programming and Principles](http://lamp.epfl.ch/teaching/progp)*. Marting Odersky. EPFL,

- *[Types and Programming Languages](http://www.cis.upenn.edu/~bcpierce/tapl/)*. Benjamin Pierce. MIT Press 2002,

- *[Practical Foundations for Programming Languages](http://www.cs.cmu.edu/~rwh/plbook/book.pdf)*. Robert Harper. Cambridge University Press 2012.