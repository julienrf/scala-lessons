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
