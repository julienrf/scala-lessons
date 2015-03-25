
# Making Real Programs

## Making an Executable Program from a Source File

- Write the following program in a file `Main.scala`:

```scala
object Main extends App {

  def fact(n: Int): Int =
    if (n <= 1) 1
    else n * fact(n - 1)

  println(fact(4))

}
```

## Making an Executable Program from a Source File (2)

- Compile it:

```bash
$ scalac Main.scala
```

- Run it:

```bash
$ scala Main
24
```

## Scaling the Build Process

- What if your program has 100 source files?

- How to recompile only the sources impacted by a given modification?

- What if my project depends on a third-party library?

- How to handle a whole project lifecycle (testing, packaging, publishing, etc.)?

## sbt

[sbt](http://www.scala-sbt.org/) is a build tool for Scala (and Java) projects

*sbt-ize* your project:

- Create a directory `my-project` for your project
- Move your `Main.scala` file into a `my-project/src/main/scala` directory
- Run it:
    - `$ sbt run` (from `my-project/` directory)
    - sbt automatically compiles the sources and calls the program entry point

## sbt Quick Start

- Run `$ sbt <command>` from your project directory to run an sbt command from your shell

- Run `$ sbt` from your project directory to open the sbt prompt

- Run sbt commands from the sbt prompt

    - `> run` to run your project
    - `> console` to open a Scala REPL in the context of your project
    - `> test` to run the tests of your project
    - `> compile` to compile your project (without running it)
