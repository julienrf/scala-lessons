# Modules

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

