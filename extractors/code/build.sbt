name := "extractors"

libraryDependencies ++= Seq(
  "net.databinder" %% "dispatch-json" % "0.8.5",
  "org.specs2" %% "specs2" % "1.8.2" % "test"
)

scalacOptions += "-unchecked"
