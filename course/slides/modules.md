# Extensibility and Modularity

## Motivation

Your game of life implementation hard codes the rules of the game

~~~ scala
object GameOfLife {
  def next(world: World): World = ???
}
~~~

- How to abstract over the rules in a way that we can easily plug a given set of rules in a given world?

## 1st Solution: Method Parameter

~~~ scala
object GameOfLife {
  def next(world: World, rules: (Cell, Int) => Cell): World = ???
}
~~~

- `rules` is a function taking a cell and its alive neighbours as parameter and returning the cell at the next generation

~~~ scala
val life = { (cell: Cell, aliveNeighbours: Int) => 
  val alive =
    if (cell.isAlive) aliveNeighbours == 2 || aliveNeighbours == 3
    else aliveNeighbours == 3
  Cell(isAlive = alive)
}
GameOfLife.next(…, life)
~~~

## 2nd Solution: Class Definition

~~~ scala
class GameOfLife(rules: (Cell, Int) => Cell) {
  def next(world: World): World = ???
}
~~~

- Given this **class definition**, `GameOfLife` is now also a type
- Its constructor has one parameter of type `(Cell, Int) => Cell`

~~~ scala
val gameOfLife = new GameOfLife(life)
gameOfLife.next(…)
~~~

- An object of type `GameOfLife` can be created using `new`

## 3rd Solution: Trait Definition

~~~ scala
trait GameOfLife {
  def rules(cell: Cell, aliveNeighbours: Int): Cell
  def next(world: World): World = ??? // code calling `rules`
}
~~~

- With this **trait definition**, `GameOfLife` has no constructor anymore
- It has an **abstract method**, `rules`

## 3rd Solution: Trait Definition (2)

~~~ scala
object Life extends GameOfLife {
  def rules(cell: Cell, aliveNeighbours: Int) = {
    val alive =
      if (cell.isAlive) aliveNeighbours == 2 || aliveNeighbours == 3
      else aliveNeighbours == 3
    Cell(isAlive = alive)
  }
}
~~~

- The `Life` object **implements** the `GameOfLife` trait
- The `next` method is **inherited** from the trait
- Unlike “sealed” trait, a simple trait can have an unlimited number of implementations

## Dynamic Method Dispatch

Consider the following program:

~~~ scala
def run(game: GameOfLife, world: World) = game.next(world)
~~~

- At runtime, the implementation of the concrete type of `game` (`Life` or another one) is called
- This process is named **dynamic method dispatch**

## Abstract Members, Encapsulation and Modularity

- Abstract members achieve **encapsulation**

- Encapsulation allows the construction of **abstraction layers**

- It is a key principle to achieve **modularity**: the underlying implementation can change without affecting users

## Specialization

`GameOfLife` is more **general** than `Life`

You could also say that `Life` is more **specialized** than `GameOfLife`

When specializing a type, you can:

- Implement abstract members
- Add new members
- Redefine non-abstract members

A subclass **conforms** to its superclass, so it can be used everywhere the superclass is needed

## Adding a Member to a Base Class

```scala
trait SemiGroup {
  def append(a: Int, b: Int) = a + b
}

trait Monoid extends SemiGroup {
  def zero = 0
}

trait Group extends Monoid {
  def inverse(a: Int) = -a
}
```

## Redefining a Member of a Base Class

```scala
trait LoggingSemiGroup extends SemiGroup {
  override def append(a: Int, b: Int) = {
    println(s"Calling append($a, $b)")
    super.append(a, b)
  }
}
```

- Redefine a member using `override`
- You can still refer to the base implementation using `super`

## Members Visibility

You can control the visibility of the members of a class:

```scala
trait Foo {
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

## Traits, Classes, Objects, Abstract Members and Parameters

- Traits, classes and objects are different “flavours” of type definition
- Objects corresponds to the special case of a type with just one value
- Traits and classes generalize several values
    - Traits can have abstract members
    - Classes can have constructor parameters

## Case Classes vs Classes

- Should you use algebraic data types or regular classes?
    - Algebraic data types are closed types (they can not be extended), but this characteristic is what makes it easier to add new operations on a type hierarchy
    - Classes are open types (they can be extended), but you can not add a new operation on a class hierarchy without changing the whole hierarchy

## Exercise

- Define a library that allows developpers to define arithmetic expressions and to evaluate them
- Write two implementations: one that focuses on the information (e.g. addition) and one that focuses on the capabilities (e.g. evaluation)
- Add new capabilities to both implementations (e.g. pretty printing an expression)
- Add new arithmetic operations to both implementations (e.g. multiplication)

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

How to build calculator that provides **both** `add` and `mul` from these two components?

## Traits

Traits can encapsulate members and can be mixed together

```scala
trait Adding {
  def add(a: Int, b: Int) = a + b
}

trait Multiplying {
  def mul(a: Int, b: Int) = a * b
}

trait Calculator extends Adding with Multiplying
```

> - Traits can be mixed in other traits or classes using `extends` and `with`

## Traits and Dynamic Dispatch

```scala
trait Simple {
  def value = 7
}

trait Doubling extends Simple {
  override def value = super.value * 2
}

trait Tripling extends Simple {
  override def value = super.value * 3
}
```

```scala
val mixin = new Simple with Doubling with Tripling
println(mixin.value) // What is printed?
```

Which implementation of the `value` member is called?

## Traits and Dynamic Dispatch (2)

![Traits Linearization](Traits.png)

## Exercise

- Abstract over the game rules in your `World` type (you can use the way you prefer)
- Implement several sets of rules:
    - See [here](http://en.wikipedia.org/wiki/Life-like_cellular_automaton#A_selection_of_Life-like_rules) for inspiration!

## Exercise

- Model the following domain in Scala:
    - An arithmetic expression *is* a number *or* an addition,
    - A number *has* a numeric value,
    - An addition *has* two terms.
    - Implement a method that evaluates the value of a specified expression
- Add a new variant for expressions: a multiplication, which *has* two factors
- Add a method that prints a specified expression

## Exercise

- Model the following domain in Scala:
    - An arithmetic expression *can* be evaluated
    - A number *is* an arithmetic expression that evaluates a number
    - An addition *is* an arithmetic expression that evaluates the sum of two arithmetic expressions
- Add a new variant for expressions: a multiplication, which evaluates to the product of two arithmetic expressions
- Add the capability to be printed to expressions
