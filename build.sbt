
lazy val root = (project in file("."))
  .settings(
    organization := "io.github.pgabara",
    name         := "sparxer",
    description  := "Submit your Apache Spark jobs via a pure REST api",
    version      := "0.1",
    scalaVersion := "2.12.8",
    
    libraryDependencies ++= Dependencies,
    scalacOptions       ++= ScalacOptions
  )
  .enablePlugins(JavaAppPackaging)

lazy val Dependencies = {
    val catsVersion = "1.5.0"
    val catsEffectVersion = "1.1.0"
    val circeVersion = "0.10.0"
    val apacheSparkVersion = "2.4.0"
    val scalatestVersion = "3.0.5"

    Seq(
        "org.typelevel"     %% "cats-core"      % catsVersion,
        "org.typelevel"     %% "cats-effect"    % catsEffectVersion,

        "io.circe"          %% "circe-core"     % circeVersion,
        "io.circe"          %% "circe-generic"  % circeVersion,
        "io.circe"          %% "circe-parser"   % circeVersion,

        "org.apache.spark"  %% "spark-core"     % apacheSparkVersion,

        "org.scalatest"     %% "scalatest"      % scalatestVersion
    )
}

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