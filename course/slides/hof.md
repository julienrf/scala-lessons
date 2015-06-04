# Higher-Order Functions

## Motivation

Consider the following solutions to the circles and spiral exercises:

```scala
def circles(n: Int): Image = {
  val circle = Circle(25 + 15 * n)
  if (n == 1) circle else circle on circles(n - 1)
}

def spiral(n: Int): Image = {
  val size = 10 + n * 2
  val dist = 50 + n * 5
  val angle = Angle.degrees((n * 36) % 360)
  val circle = Circle(size).at(dist * angle.sin, dist * angle.cos)
  if (n == 1) circle else circle on spiral(n - 1)
}
```

- Note the similarities between the last line of each method

## Motivation (2)

Here is an attempt to capture the loop and abstract over the images:

```scala
def stack(image: Image, n: Int): Image =
  if (n == 1) image else image on stack(image, n - 1)

def circles(count: Int) = stack(???, count)
```

> - We see that we can not supply the `image` parameter because it depends on the value of `n`
> - How to abstract over the **computation** that returns the image?

## Abstract Over Computations

```scala
def stack(image: Int => Image, n: Int): Image =
  if (n == 1) image(n) else image(n) on stack(image, n - 1)

def circles(count: Int) = stack(n => Circle(25 + 15 * n), count)
```

- The parameter `image` has type `Int => Image`, that is a **function** taking an `Int` and returning an `Image`
- We define `circles` by supplying a **function literal**

## Function Types and Function Values

- The type `(T1, …, Tn) => R` is the type of a function that takes `n` parameters (of type `T1`, …, `Tn`) and returns a value of type `R`
    - `Int => Int` is the type of a function that takes an `Int` and returns an `Int`

- The value `(t1: T1, …, tn: Tn) => <expr>` is a function that takes `n` parameters (`t1`, …, `tn`) and which body is `<expr>`
    - `(a: Int, b: Int) => a + b` is a function that takes two parameters `a` and `b` and returns their sum

## Exercise

- Rewrite `spiral` using `stack`

<!--
def spiral(count: Int) = stack(n => {
  val size = 10 + n * 2
  val dist = 50 + n * 5
  val angle = Angle.degrees((n * 36) % 360)
  Circle(size).at(dist * angle.sin, dist * angle.cos)
}, count)
-->

## Exercise

- Note the similarities between `stack` and `barbells`. Abstract over their differences by defining the following `layout` method:

```scala
def layout(op: (Image, Image) => Image, image: Int => Image, n: Int): Image
```

So that `stack` and `barbells` can be rewritten as follows:

```scala
def stack(image: Int => Image, count: Int) =
  layout((img1, img2) => img1 on img2, image, count)

def barbells(count: Int) =
  layout((img1, img2) => img1 above img2, n => barbell(weight(15)), count)
```

<!--
def layout(op: (Image, Image) => Image, image: Int => Image, n: Int): Image =
  if (n == 1) image(n) else op(image(n), layout(op, image, n - 1))
-->

## Exercise

- Rewrite `sierpinski` using `layout`

<!--
def sierpinski(count: Int) =
  layout((img1, img2) => img2 above (img2 beside img2), n => Triangle(10, 10) fillColor Color.black, count)
-->

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
