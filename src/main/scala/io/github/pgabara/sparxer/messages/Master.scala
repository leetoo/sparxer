package io.github.pgabara.sparxer.messages

import cats.Show
import cats.implicits._

sealed trait Master

object Master extends MasterInstances {

  sealed trait NumberOfWorkerThreads
  final case class Strict(n: Int) extends NumberOfWorkerThreads
  final case object LogicCoresNumber extends NumberOfWorkerThreads

  final case class Local(workerThreads: NumberOfWorkerThreads, maxFailures: Option[Int]) extends Master
  final case class SparkCluster(uri: String) extends Master
  final case class Mesos(uri: String) extends Master
  final case class K8s(uri: String) extends Master
  final case object Yarn extends Master
}

trait MasterInstances {

  import Master._

  implicit val masterShowInstance: Show[Master] =
    Show.show {
      case SparkCluster(uri)  => uri
      case local: Local       => local.show
      case Yarn               => "yarn"
      case Mesos(uri)         => uri
      case K8s(uri)           => uri
    }

  implicit val localMasterShowInstance: Show[Local] =
    Show.show { local =>
      local.workerThreads match {
        case Strict(n) =>
          local.maxFailures.fold(s"local[$n]")(f => s"local[$n,$f]")
        case LogicCoresNumber =>
          local.maxFailures.fold("local[*]")(f => s"local[*,$f]")
      }
    }
}