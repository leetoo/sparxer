package io.github.pgabara.sparxer.engine

import java.io.File

import cats.effect.Sync
import io.github.pgabara.sparxer.messages.SubmitSparkJob
import org.apache.spark.launcher.SparkLauncher
import cats.implicits._

trait SparkSubmit[F[_]] {
  def submit(a: SubmitSparkJob): F[Subscription]
}

final case class Subscription(id: String)

final class SparkSubmitInterpreter[F[_]: Sync] extends SparkSubmit[F] {

  override def submit(job: SubmitSparkJob): F[Subscription] =
    configureLauncher(job).map(_.startApplication()).map(_ => Subscription(job.metadata.id))

  private[engine] def configureLauncher(job: SubmitSparkJob): F[SparkLauncher] = {
    def redirectOutputTo(id: String): F[File] = ???

    redirectOutputTo(job.metadata.id).map { redirect =>
      val builder = new SparkLauncher()
        .setAppName(job.metadata.name)
        .setAppResource(job.pathToJar)
        .setMainClass(job.mainClass)
        .setMaster(job.master.show)
        .setDeployMode(job.deployMode.show)
        .addAppArgs(job.args: _*)
        .setVerbose(???) // todo: provide from a configuration file
        .setSparkHome(???) // todo: provide from a configuration file
        .redirectOutput(redirect)
      job.sparkConf.foldLeft(builder) { case (b, (key, value)) => b.setConf(key, value) }
    }
  }
}


