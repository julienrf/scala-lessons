name := "scala-course"

scalaVersion := "2.11.5"

resolvers += "Underscore Training" at "https://dl.bintray.com/underscoreio/training"

libraryDependencies += "underscoreio" %% "doodle" % "0.1.0"

initialCommands in console := """
  |import doodle.core._
  |import doodle.syntax._
  |import doodle.jvm._
""".trim.stripMargin
