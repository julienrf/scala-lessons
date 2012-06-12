package polymorphism.examples;

public class Unsound {

    public static class Animal {}

    public static class Mammal extends Animal {}
    public static class Cat extends Mammal {}

    public static class Crocodile extends Animal {}


    public static void yeah() {

        Mammal[] mammals = new Mammal[] {new Cat()};

        processAnimals(mammals);

    }

    public static void processAnimals(Animal[] animals) {
        animals[0] = new Crocodile();
    }

}
