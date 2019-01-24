import sbt._


object Dependencies {

  val cats          = "org.typelevel"         %% "cats-core"            % "1.5.0"
  val catsEffect    = "org.typelevel"         %% "cats-effect"          % "1.1.0"

  val logback       = "ch.qos.logback"         % "logback-classic"      % "1.2.3"
  val log4cats      = "io.chrisdavenport"     %% "log4cats-slf4j"       % "0.3.0-M2"

  val circeCore     = "io.circe"              %% "circe-core"           % "0.10.0"
  val circeParser   = "io.circe"              %% "circe-parser"         % "0.10.0"
  val circeGeneric  = "io.circe"              %% "circe-generic"        % "0.10.0"

  val http4sDSL     = "org.http4s"            %% "http4s-dsl"           % "0.20.0-SNAPSHOT"
  val http4sServer  = "org.http4s"            %% "http4s-blaze-server"  % "0.20.0-SNAPSHOT"

  val pureConfig    = "com.github.pureconfig" %% "pureconfig"           % "0.10.1"

  val apacheSpark   = "org.apache.spark"      %% "spark-core"           % "2.4.0"

  val scalatest     = "org.scalatest"         %% "scalatest"            % "3.0.5"   % "test"
}
