# Type Polymorphism

## Type Polymorphism

Until now, you saw only how to abstract over values

It is also possible to abstract over **types**

## Exercise

<!-- FIXME Use `identity(n: Int): Int`, `identity(b: Boolean): Boolean` as an example? -->

* Add a method `forAll(p: Int => Boolean): Boolean` to `IntList`, that tests if the predicate `p` holds for all the elements of the list

* Then add a method `hasEvenSize: Boolean` that tests if the list has an even size

* Generalize `forAll` and `hasEvenSize` by adding a method `foldBool(z: Boolean, op: (Int, Boolean) => Boolean): Boolean`

> * Note that the implementations of `fold` and `foldBool` are **exactly the same**.

> * Is it possible to write only one `fold` function that would work with both `Int` and `Boolean`?

## Polymorphic Functions

Look at the type signatures of `fold` and `foldBool`:

```scala
(Int,     (Int, Int)     => Int)     => Int
(Boolean, (Int, Boolean) => Boolean) => Boolean
```

You could add a `foldString` with the following type signature:

```scala
(String,  (Int, String)  => String)  => String
```

It always follows this pattern:

```scala
(A, (Int, A) => A) => A
```

## Polymorphic Functions (2)

Functions can have **type parameters**:

```scala
def fold[A](z: A, op: (Int, A) => A): A
```

- `A` is a **type parameter** (**universally** quantified), `fold` is a **polymorphic function**

You can then call `fold` as follows:

```scala
def sum = fold[Int](0, (n, s) => s + n)
def forAll(p: Int => Boolean) = fold[Boolean](true, (n, b) => b && p(n))
```

>   - Note that if you use the following signature you can help the type inference mechanism and omit the applied type in most cases:
>
>     ```scala
>     def fold[A](z: A)(op: (Int, A) => A): A
>     ```
>
>     ```scala
>     def sum = fold(0)((n, s) => s + n)
>     ```

## Exercise

* Make `fold` polymorphic

## Exercise

* Implement an abstract data type representing a list of `String` elements:

```scala
abstract class StringList {
  def add(str: String): StringList
  def concat(ss: StringList): StringList
}
```

> - Note the strong similarities with `IntList`
> - You want to abstract over the type of the elements of the list

## Type Constructors

Types can have type parameters:

```scala
abstract class List[A] {
  def add(element: A): List[A]
  def concat(as: List[A]): List[A]
}
```

> - `List` is a **type constructor**: it takes a type as parameter and yields another type
>     - E.g. `List[Int]`, `List[String]`, etc.

## Exercise

* Write a polymorphic `List[A]` data type:

```scala
abstract class List[A] {
  def add(element: A): List[A]
  def concat(as: List[A]): List[A]
  def fold[B](z: B)(op: (A, B) => B): B
}
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

* Make the `List` class covariant



# Type Classes and Implicit Parameters

## Motivating Problem

Remember the `sum` method of `IntList`?

```scala
def sumInts(xs: List[Int]): Int = xs.fold(0)(_ + _)
```

What if we want to compute the sum of a list of complex numbers?

```scala
def sumComplexes(zs: List[Complex]): Complex =
  zs.fold(new Complex(0, 0))(_ add _)
```

- Can we abstract over the similarities of `sumInts` and `sumComplexes`?

## Motivating Problem (2)

```scala
trait Sumable[A <: Sumable[A]] {
  def + (that: A): A
}

def sum[A <: Sumable[A]](as: List[A], zero: A): A =
  as.fold(zero)(_ + _)
```

We can change the implementation of `Complex` to extend `Sumable[Complex]`:

```scala
trait Complex extends Sumable[Complex] {
  def + (that: Complex) = add(that)
  …
}
```

```scala
val zs = List(new Complex(1, 2), new Complex(3, 4))
sum(zs, new Complex(0, 0))
```

## Motivating Problem (3)

- We can not change the implementation of `Int` to make it extend `Sumable[Int]`

- Anyway, this `Sumable` trait captures only a part of the problem: we still need to manually supply the identity element corresponding to each type

## (Almost) Type Classes

```scala
trait Sumable[A] {
  def zero: A
  def append(a1: A, a2: A): A
}
```

```scala
def sum[A](as: List[A], A: Sumable[A]) =
  as.fold(A.zero)(A.append)
```

## Retroactive Extension

```scala
val sumableInt = new Sumable[Int] {
  val zero = 0
  def append(a1: Int, a2: Int) = a1 + a2
}
```

```scala
val xs = List(1, 2, 3, 4)
sum(xs, sumableInt)
```

## Retroactive Extension (2)

```scala
val sumableComplex = new Sumable[Complex] {
  val zero = new Complex(0, 0)
  def append(a1: Complex, a2: Complex) = a1.add(a2)
}
```

```scala
val zs = List(new Complex(1, 2), new Complex(3, 4))
sum(zs, sumableComplex)
```

## (Almost) Type Classes (2)

- The `Sumable[A]` trait captures everything we need to make a sum of a list of `A` (the identity element and the binary operation)

- However, we need to explicitly supply the instance corresponding to each type

    - To compute a sum of `Int` we explicitly pass `sumableInt`

    - To compute a sum of `Complex` we explicitly pass `sumableComplex`

## Implicit Parameters

```scala
def sum[A](as: List[A])(implicit A: Sumable[A]) =
  as.fold(A.zero)(A.append)
```

`sum` takes an **implicit parameter** of type `Sumable[A]`

If you define `sumableInt` and `sumableComplex` as **implicit values** you can omit to supply them when calling `sum`:

```scala
implicit val sumableInt = …
implicit val sumableComplex = …
```

```scala
sum(xs)
sum(zs)
```

## Implicit Parameters (2)

If you call a method without supplying its implicit parameters, the compiler tries to resolve them in the **implicit scope**

The implicit scope is basically built using (by order of priority, highest first):

- implicit values of the current lexical scope or outer scopes,

- explicitly imported implicit values (e.g. `import path.to.some.Implicits._`),

- the implicit values of companion objects of the implicit parameter’s type

## Context Bounds

```scala
def sum[A : Sumable](as: List[A]) = {
  val A = implicitly[Sumable[A]]
  xs.fold(A.zero)(A.append)
}
```

- `A : F` expands to an implicit parameter of type `F[A]`

- We say that `F` is a **context bound** for `A`

- You can retrieve an implicit parameter using the `implicitly` helper

- Sometimes the context bound notation is shorter than using an implicit parameter list

