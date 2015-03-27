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

## Sequencing Computations

Actually, `flatMap` and `map` functions are very useful to sequence computations within a given *context* (optional values, collections, and much more)

They are so common that Scala supports a more convenient syntax, the **`for` notation**, that desugars to `flatMap`, `map` and `filter` calls

## Sequencing Computations

Remember the `lightenLightenLoad` method:

```scala
def lightenLightenLoad(barbell: Barbell): Option[Int] =
  barbell.lighten.flatMap { lighterBarbell =>
    lighterBarbell.lighten.map { lighterLighterBarbell =>
      lighterLighterBarbell.load
    }
  }
```

You can rewrite it as follows:

~~~ scala
def lightenLightenLoad(barbell: Barbell): Option[Int] =
  for {
    lighterBarbell <- barbell.lighten
    lighterLighterBarbell <- lighterBarbell.lighten
  } yield lighterLighterBarbell.load
~~~

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

## Tuples (2)

- The type `(T1, …, Tn)` is a tuple type of `n` elements which i^th^ element has type `Ti`
- The value `(t1, …, tn)` is a tuple value of `n` elements

## Tuples (3)

```scala
val qr = euclideanDiv(42, 10)
println(qr._1)
println(qr._2)
```

- Get the i^th^ element of a tuple with the member `_i`

```scala
val (q, r) = euclideanDiv(42, 10)
```

- Or use a **tuple pattern** to *deconstruct* a tuple


## Named Parameters

```scala
Range(1, 10, 2)
```

It is difficult to figure out what the above program does: what is the meaning of these numbers?

Using **named parameters** can improve readability:

```scala
Range(start = 1, end = 10, step = 2)
```

## Default Arguments

~~~ scala
def square(size: Int = 50) = Rectangle(size, size)

val defaultSquare = square()
val customSquare = square(100)
~~~

## Repeated Parameters

~~~ scala
def stack(images: Image*): Image =
  images.foldLeft(emptyImage)((image, result) => image on result)

stack(Circle(50))
stack(Circle(50), Rectangle(50, 50))
~~~

~~~ scala
val images = Seq(Circle(50), Circle(60))
stack(images: _*)
~~~
