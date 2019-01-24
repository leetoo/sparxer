package io.github.pgabara.sparxer.api

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.HttpRoutes
import org.http4s.server.blaze.BlazeServerBuilder

import cats.implicits._
import org.http4s.dsl.io._
import org.http4s.implicits._

object Server extends IOApp {

  private def app = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name => Ok(s"Hello, $name.")
    case GET -> Root / "machine"      => Ok(java.net.InetAddress.getLocalHost.getHostAddress)
  }.orNotFound

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
    .bindHttp(port = 8080, host = "0.0.0.0")
    .withHttpApp(app)
    .serve
    .compile
    .drain
    .as(ExitCode.Success)
}
