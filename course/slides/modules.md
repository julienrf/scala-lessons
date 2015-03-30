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
class GameOfLife(rules: (Cell, Seq[Cell]) => Cell) {
  def next(world: World): World = ???
}
~~~

- Given this **class definition**, `GameOfLife` is now also a type
- Its constructor has one parameter of type `(Cell, Seq[Cell] => Cell)`

~~~ scala
val gameOfLife = new GameOfLife(life)
gameOfLife.next(…)
~~~

- An object of type `GameOfLife` can be created using `new`

## 3rd Solution: Trait Definition

~~~ scala
trait GameOfLife {
  def rules(cell: Cell, neighbours: Seq[Cell]): Cell
  def next(world: World): World = ??? // code calling `rules`
}
~~~

- With this **trait definition**, `GameOfLife` has no constructor anymore
- It has an **abstract method**, `rules`

## 3rd Solution: Trait Definition (2)

~~~ scala
object Life extends GameOfLife {
  def rules(cell: Cell, neighbours: Seq[Cell]) = {
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

```scala
def mulComplexes(x: Complex, y: Complex) = x.mul(y)
```

At runtime, the implementation of the concrete type of `x` (`ComplexRectangular` or `ComplexPolar`) is called

This process is named **dynamic method dispatch**

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

