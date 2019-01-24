import Dependencies._

lazy val root = (project in file("."))
  .aggregate(engine, api, messages)

lazy val engine = sparxerProject("engine")
  .settings(
    packageName := moduleName.value,
    libraryDependencies ++= EngineDependencies
  )
  .dependsOn(messages)
  .enablePlugins(JavaAppPackaging)

lazy val api = sparxerProject("api")
  .settings(
    packageName := moduleName.value,
    libraryDependencies ++= ApiDependencies
  )
  .dependsOn(messages)
  .enablePlugins(JavaAppPackaging)

lazy val messages = sparxerProject("messages")

val ApiDependencies = Seq(
  circeCore, circeParser, circeGeneric, http4sDSL, http4sServer)

val EngineDependencies = Seq(apacheSpark)

lazy val commonSettings = Seq(
  organization  := "io.github.pgabara",
  name          := "sparxer",
  description   := "Submit your Apache Spark jobs via a pure REST api",
  version       := "0.0.1",
  scalaVersion  := "2.12.8",
  
  scalafmtOnCompile := true,

  scalacOptions       ++= ScalacOptions,
  resolvers           +=  Resolver.sonatypeRepo("snapshots"),
  libraryDependencies ++= Seq(cats, catsEffect, logback, log4cats, pureConfig, scalatest)
)

lazy val ScalacOptions = Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Ywarn-infer-any",
  "-Ywarn-unused-import",
  "-Xfatal-warnings",
  "-Xlint"
)

def sparxerProject(name: String) =
  Project(name, file(name))
    .settings(commonSettings)
    .settings(moduleName := s"sparxer-$name")