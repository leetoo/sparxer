package io.github.pgabara.sparxer.api

import cats.implicits._
import cats.effect.{ExitCode, IO, IOApp}
import io.chrisdavenport.log4cats.{Logger, SelfAwareStructuredLogger}
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s.HttpRoutes
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.dsl.io._
import org.http4s.implicits._

object Server extends IOApp {

  implicit def logger: SelfAwareStructuredLogger[IO] = Slf4jLogger.getLogger[IO]

  private def app = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Logger[IO].info(s"Saying hello to '$name'") *> Ok(s"Hello, $name.")
    case GET -> Root / "machine" =>
      for {
        host     <- IO.delay(java.net.InetAddress.getLocalHost.getHostName)
        _        <- Logger[IO].info(s"Machine host: $host")
        response <- Ok(host)
      } yield response
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
