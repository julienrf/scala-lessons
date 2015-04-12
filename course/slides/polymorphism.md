# Type Polymorphism

## Type Polymorphism

Until now, you saw only how to abstract over values

It is also possible to abstract over **types**

## Motivation

Consider the following methods:

~~~ scala
def intIdentity(x: Int): Int = x
~~~

~~~ scala
def boolIdentity(b: Boolean): Boolean = b
~~~

> - Their implementation is the same

## Motivation (2)

- You could generalize this pattern for any other type
- For instance, with `String`:

~~~ scala
def stringIdentity(s: String): String = s
~~~

> - How to write a general `identity` method that works with any type?

## Polymorphic Methods

Look at the type signatures of `intIdentity`, `boolIdentity` and `stringIdentity`:

```scala
Int     => Int
Boolean => Boolean
String  => String
```

It always follows this pattern:

```scala
A       => A
```

## Polymorphic Methods (2)

Methods can have **type parameters**:

```scala
def identity[A](a: A): A = a
```

- `A` is a **type parameter** (**universally** quantified), `identity` is a **polymorphic method**

You can then call `identity` as follows:

```scala
val x = identity[Int](42)
val b = identity[Boolean](true)
val s = identity[String]("foo")
```

>   - Note that the type parameter can actually be inferred from the argument type:
>
>     ```scala
>     val x = identity(42)
>     ```

## Exercise

- Implement the following method that takes a collection as parameter and returns its number of elements:

~~~ scala
def size[A](as: Seq[A]): Int = ???
~~~

## Exercise

- Implement the following method that concatenates two sequences:

~~~ scala
def concat[A](as1: Seq[A], as2: Seq[A]): Seq[A] = ???
~~~

## Exercise

- Implement the following method that reverses a sequence:

~~~ scala
def reverse[A](as: Seq[A]): Seq[A] = ???
~~~

## Type Constructors

Types can have type parameters:

```scala
trait Seq[A] {
  def size: Int
  def concat(as: Seq[A]): Seq[A]
  def reverse: Seq[A]
}
```

> - `Seq` is a **type constructor**: it takes a type as parameter and yields another type
>     - E.g. `Seq[Int]`, `Seq[String]`, etc.

## Exercise

* Write a polymorphic `Col[A]` data type:

```scala
sealed trait Col[A] {
  def size: Int = ???
  def concat(as: Col[A]): Col[A] = ???
  def reverse: Col[A] = ???
  def map[B](f: A => B): Col[B] = ???
  def filter(p: A => Boolean): Col[A] = ???
  def forall(p: A => Boolean): Boolean = ???
  def exists(p: A => Boolean): Boolean = ???
  def fold[B](b: B)(f: (A, B) => B): B = ???
}

case class Empty[A]() extends Col[A]
case class OneAnd[A](a: A, tail: Col[A]) extends Col[A]
```

## Type Quantification

Subtyping and universal types are two different generalization mechanisms. Can you mix them together?

Consider the following class hierarchy:

```scala
trait Animal {
  def fitness: Int
}

trait Reptile extends Animal

trait Mammal extends Animal

trait Zebra extends Mammal {
  def zebraCount: Int
}

trait Giraffe extends Mammal
```

You want to write a function `selection`, that takes two animals as parameters and returns the one with the highest `fitness` value. What will its type signature be?

## Type Quantification (2)

A possible solution using subtyping is the following:

```scala
def selection(a1: Animal, a2: Animal) =
  if (a1.fitness > a2.fitness) a1 else a2
```

What is the problem with this solution?

>   - The return type is `Animal`: call it with two `Zebra`s and you get only an `Animal`
>       - You loose the ability to access further its `zebraCount` member

## Type Quantification (3)

We could try to solve this issue using a universal type:

```scala
def selection[A](a1: A, a2: A): A =
  if (a1.fitness > a2.fitness) a1 else a2
```

But this solution does not compile: we can not access the `fitness` member of `a1` and `a2` because `A` is not constrained to be a subtype of `Animal`

## Upper and Lower Bounds

We can write a better solution using a **bounded quantification**:

```scala
def selection[A <: Animal](a1: A, a2: A): A =
   if (a1.fitness > a2.fitness) a1 else a2
```

- `A <: Animal` means “for all type `A` that is a subtype of `Animal`”, and we say that `Animal` is the **upper bound** of `A`

Similarly, you can constraint a type parameter `A` to have a **lower bound** `B`:

 - `A >: B`

## Variance

Consider the following type modeling a zoo run containing an animal:

```scala
abstract class Run[A] {
  def get: A // Get the animal that lives in this run
}
```

Mammal well being is a serious subject, the following function checks that a given run has the appropriate size regarding to the animal that lives in (provided you have methods in `Run[A]` and `Mammal` that give you their size):

```scala
def isLargeEnough(run: Run[Mammal]): Boolean = …
```

What happens if you pass it a `Run[Zebra]` as parameter?

## Variance (2)

```scala
scala> isLargeEnough(zebraRun)
<console>:14: error: type mismatch;
 found   : Run[Zebra]
 required: Run[Mammal]
```

A `Run[Zebra]` is not a subtype of `Run[Mammal]`, in fact they are just two different types without any subtyping relation. Could it be otherwise? Should it be otherwise?

> - Intuitively, a run with a zebra is just a special case of a run with a mammal
> - `isLargeEnough` can only use the `get` member of its `Run[Mammal]` parameter, and expects to get a `Mammal` value
> - A `Run[Zebra]` value has a `get` member that returns a `Zebra`, which is a `Mammal`
> - It seems that `Run[Zebra]` should be a subtype of `Run[Mammal]`

## Covariance

More generally, `Run[A] <: Run[B]` if `A <: B`

We say that `A` is **covariant** in `Run[A]` and we can tell it to the compiler as follows:

```scala
abstract class Run[+A] {
  def get: A
}
```

Now the compiler accepts that you pass a `Run[Zebra]` where a `Run[Mammal]` is expected

## Variance (3)

Consider the following type modeling a veterinary able to treat animals:

```scala
abstract class Vet[A] {
  def treat(a: A)
}
```

And the following function treating all the mammals of the zoo using a veterinary passed as parameter:

```scala
def treatMammals(vet: Vet[Mammal]) { … }
```

What happens if you pass it a `Vet[Animal]` as parameter?

## Variance (4)

```scala
scala> treatMammals(animalVet)
<console>:14: error: type mismatch;
 found   : Vet[Animal]
 required: Vet[Mammal]
```

A `Vet[Animal]` is not a subtype of `Vet[Mammal]`, they are just two different types. Should it be otherwise?

> - Intuitively, a veterinary that can treat animals can treat mammals, because a mammal is just a special case of an animal
> - `treatMammals` can only use the `treat` member of its `Vet[Mammal]` parameter, and can pass it any `Mammal` value
> - A `Vet[Animal]` has a `treat` member that takes an `Animal` as parameter, so it can take a `Mammal` as well, because `Mammal` is a subtype of `Animal`
> - It seems that `Vet[Animal]` should be a subtype of `Vet[Mammal]`

## Contravariance

More generally, `Vet[A] <: Vet[B]` if `A >: B`

We say that `A` is **contravariant** in `Run[A]` and we can tell it to the compiler as follows:

```scala
abstract class Vet[-A] {
  def treat(a: A)
}
```

Now the compiler accepts that you pass a `Vet[Animal]` where a `Vet[Mammal]` is expected

## Variance (5)

For a type constructor `F[X]`, variance annotations allow you to define the subtyping relation of the types `F[A]` and `F[B]` according to the subtyping relation of the types `A` and `B`

By default, type parameters are **invariant**

## Exercise

* Make the `Col[A]` type covariant


# Type Classes and Implicit Parameters

## Motivation

Remember the `sum` and `stack` methods:

```scala
def sum(xs: Seq[Int]): Int = xs.foldLeft(0)(_ + _)
```

```scala
def stack(is: Seq[Image]): Image =
  is.foldLeft(Circle(0).lineColor(Color.white))(_ on _)
```

> - Can you capture the ability for a type to have an identity element and to be combined through a binary operation?

## Motivation (2)

~~~ scala
def genericSum(ss: Seq[Sumable]): Sumable =
  ss.foldLeft(???)((acc, a) => acc + a)
~~~

~~~ scala
trait Sumable {
  def + (that: Sumable): Sumable
}
~~~

~~~ scala
val sumImages = genericSum(Seq(Circle(10), Circle(20)))
~~~

> - Problem: `genericSum` returns a `Sumable` instead of an `Image`

## Motivation (3)

~~~ scala
def genericSum[A <: Sumable](ss: Seq[A]): A =
  ss.foldLeft(???)((acc, a) => acc + a)
~~~

~~~ scala
class Image extends Sumable {
  def + (that: Sumable): Sumable = this on that
  def on(that: Image): Image = ???
}
~~~

> - Problem: `Sumable` forces to return a `Sumable` instead of an `Image`

## Motivation (4)

~~~ scala
def genericSum[A <: Sumable[A]](ss: Seq[A]): A =
  ss.foldLeft(???)((acc, a) => acc + a)
~~~

~~~ scala
trait Sumable[A <: Sumable[A]] {
  def + (that: A): A
}
~~~

~~~ scala
class Image extends Sumable[Image] {
  def + (that: Image): Image = this on that
  def on(that: Image): Image = ???
}
~~~

> - Problem: we can not automatically get the `zero` value for a given `Sumable` type

## Motivation (5)

~~~ scala
def genericSum[A <: Sumable[A]](zero: A, ss: Seq[A]): A =
  ss.foldLeft(zero)((acc, a) => acc + a)
~~~

~~~ scala
val sumImages = genericSum(
  Circle(0).lineColor(Color.white),
  Seq(Circle(10), Circle(20))
)

val sumInts = genericSum(0, Seq(1, 2, 3))
~~~

> - Problem: `Int` does not extend `Sumable`

## Motivation (6)

Let’s recap our goals:

- Capture the ability for a type to **both** have a identity element and to be combined through a binary operation
- **Retroactively** add this ability to any type

## Toward Type Classes

```scala
def sum(xs: Seq[Int]): Int =
  xs.foldLeft(0)(_ + _)
```

```scala
def stack(is: Seq[Image]): Image =
  is.foldLeft(Circle(0).lineColor(Color.white))(_ on _)
```

~~~ scala
def genericSum[A](as: Seq[A], zero: A, combine: (A, A) => A): A =
  as.foldLeft(zero)(combine)
~~~

## (Almost) Type Classes

```scala
trait Sumable[A] {
  def zero: A
  def combine(a1: A, a2: A): A
}
```

```scala
def genericSum[A](as: Seq[A], sumable: Sumable[A]) =
  as.foldLeft(sumable.zero)(sumable.combine)
```

## Retroactive Extension

```scala
val sumableInt = new Sumable[Int] {
  val zero = 0
  def combine(a1: Int, a2: Int) = a1 + a2
}
```

```scala
val xs = Seq(1, 2, 3, 4)
genericSum(xs, sumableInt)
```

## Retroactive Extension (2)

```scala
val sumableImage = new Sumable[Image] {
  val zero = Circle(0).lineColor(Color.white)
  def combine(a1: Complex, a2: Complex) = a1 on a2
}
```

```scala
val images = Seq(Circle(10), Circle(20))
genericSum(images, sumableImage)
```

## (Almost) Type Classes (2)

- The `Sumable[A]` trait captures everything we need to compute the “sum” of a sequence of `A` values (the identity element and the binary operation)

- However, we need to explicitly supply the instance corresponding to each type

    - To compute a sum of `Int` we explicitly pass `sumableInt`

    - To compute a sum of `Image` we explicitly pass `sumableImage`

## Implicit Parameters

```scala
def genericSum[A](as: List[A])(implicit sumable: Sumable[A]) =
  as.foldLeft(sumable.zero)(sumable.combine)
```

`genericSum` takes an **implicit parameter** of type `Sumable[A]`

If you define `sumableInt` and `sumableImage` as **implicit values** you can omit to supply them when calling `genericSum`:

```scala
implicit val sumableInt: Sumable[Int] = …
implicit val sumableImage: Sumable[Image] = …
```

```scala
genericSum(xs)
genericSum(images)
```

## Implicit Parameters (2)

If you call a method without supplying its implicit parameters, the compiler tries to resolve them from the **implicit scope**

The implicit scope is basically built using (by order of priority, highest first):

- implicit values of the current lexical scope or outer scopes,

- explicitly imported implicit values (e.g. `import path.to.some.Implicits._`),

- the implicit values of companion objects of the implicit parameter’s type

## Context Bounds

```scala
def genericSum[A : Sumable](as: List[A]) = {
  val sumable = implicitly[Sumable[A]]
  xs.fold(sumable.zero)(sumable.combine)
}
```

- `A : F` expands to an implicit parameter of type `F[A]`
- We say that `F` is a **context bound** for `A`
- You can retrieve an implicit parameter using the `implicitly` method
- Sometimes the context bound notation is shorter than using an implicit parameter list

## Exercise

- Define a type class `Show[A]` that captures the ability to compute an image for a given value
- Implement an instance of `Show[FitnesDevice]`
