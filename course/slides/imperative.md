
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

# Mutable Data Types

## Mutable Data Types

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
