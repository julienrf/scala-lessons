
# Imperative Programming

## Motivating Problem: Random Number Generator

Try to implement the following random number generator:

```scala
def next: Int = ???
```

> Hint: a simple way to generate a sequence of pseudorandom values is to use the following recurrence relation:
>
> x~n+1~ = 22695477 x~n~ + 1

Example of use:

```scala
println(next)
println(next)
```

> - The `next` method is impossible to implement!

## Motivating Problem: Random Number Generator

By changing the `next` signature, a possible implementation would be the following:

```scala
def next(x: Int) = 22695477 * x + 1
```

But users would be required to *remember* each value to get the next one:

```scala
val x1 = next(1)
println(x1)
val x2 = next(x1)
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

## Stateful Computations

`next` can be implemented as follows using a `var`:

```scala
var x = 1
def next() = {
  x = 22695477 * x + 1
  x
}
```

- The `x` var contains the random number generator’s **state**

## Stateful Classes

~~~ scala
class RNG {
  private var x = 1
  def next() = {
    x = 22695477 * x + 1
    x
  }
}
~~~

- This code is a **class definition** which introduces :
    - the `RNG` type
    - the `RNG` constructor

## Stateful Classes

~~~ scala
class RNG {
  private var x = 1
  def next() = {
    x = 22695477 * x + 1
    x
  }
}
~~~

- The `RNG` type has two members:
    - `x`, which is **private** (not reachable from the outside)
    - `next`
- The `RNG` class **encapsulates** its state

## Benefits of Assignment

- **Stateful objects** help keeping code modular

    - The stateful version of `RNG` does not require users to explicitly manipulate objects state (by passing an additional parameter)

## Identity and State

```scala
val rng1 = new RNG
val rng2 = new RNG
```

Are `rng1` and `rng2` the same objects? At first glance they seem to be the same…

```scala
println(rng1.next()) // “22695478”
println(rng1.next()) // “-2138921681”
println(rng2.next()) // “22695478”
```

`rng1` and `rng2` have distinct **effects**, they are not equals in a sense that we can not substitute one by the other

The introduction of assignment leads to the loss of **referential transparency** and makes reasonning about programs drastically more difficult

## Classes vs Case Classes: Identity

~~~ scala
val rng1 = new RNG
val rng2 = new RNG
rng1 == rng2
~~~

> - `false`

~~~ scala
val barbell1 = Barbell(20, 150)
val barbell2 = Barbell(20, 150)
barbell1 == barbell2
~~~

> - `true`

## Stateful Objects, Aliasing and Copying

```scala
val rng1 = new RNG
val rng2 = rng1
```

Giving two different names to the *same* object can be confusing: applying a method on `rng1` will have an effect on `rng2`!

Stateful objects force you to distinguish between the intents of **aliasing** and **copying**

This problem does not exist with **immutable objects**

## Classes vs Case Classes: Aliasing

~~~ scala
val rng1 = new RNG
val rng2 = rng1
~~~

~~~ scala
val barbell1 = Barbell(20, 150)
val barbell2 = barbell1
~~~

- Immutable objects (ie. defined by case classes) can be freely aliased (it is actually a **good practice** to reduce the memory consumption)

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

## Safe Usage of Vars

- As long as side-effects are not observable from the outside, using a var is safe:

~~~ scala
def sum(xs: Seq[Int]) = {
  var result = 0
  for (x <- xs) {
    result = result + x
  }
  result
}
~~~