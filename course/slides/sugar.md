# Syntactic Sugars

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

# Functions

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

# Tuples

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


