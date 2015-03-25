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

