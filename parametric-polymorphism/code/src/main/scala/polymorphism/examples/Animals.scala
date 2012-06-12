package polymorphism.examples

object Animals {

  trait Animal

  class Reptile extends Animal

  trait Mammal extends Animal

  class Cat extends Mammal

  class Dog extends Mammal

}