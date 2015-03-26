
# Making Standalone Programs

## Making a Standalone Program from a Source File

- Write the following program in a file `Main.scala`:

```scala
object Main extends App {

  println("Hello world!")

}
```

## Making an Executable Program from a Source File (2)

- Compile it:

```bash
$ scalac Main.scala
```

(it should produce `*.class` files)

- Run it:

```bash
$ scala Main
"Hello world!"
```

## Scaling the Build Process

- What if your program has 100 source files?

- How to recompile only the sources impacted by a given modification?

- What if your project depends on a third-party library?

- How to handle a whole project lifecycle (testing, packaging, publishing, etc.)?

## sbt

[sbt](http://www.scala-sbt.org/) is a build tool for Scala (and Java) projects

## sbt Quick Start

- Run `$ sbt <command>` from your project directory to run an sbt command from your shell

- Run `$ sbt` from your project directory to open the sbt prompt

- Run sbt commands from the sbt prompt

    - `> run` to run your project
    - `> console` to open a Scala REPL in the context of your project
    - `> test` to run the tests of your project
    - `> compile` to compile your project (without running it)

## sbt Quick Start (2)

- The build process of the project is defined in a `build.sbt` file
- This file essentially defines your project’s **settings**:

    ```scala
    name := "my-project"
    
    version := "1.0"
    
    libraryDependencies += "com.chuusai" %% "shapeless" % "2.1.0"
    ```

- Settings values are Scala expressions
