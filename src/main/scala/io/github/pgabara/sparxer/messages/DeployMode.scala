package io.github.pgabara.sparxer.messages

import cats.Show

sealed trait DeployMode

object DeployMode {

  final case object Client extends DeployMode
  final case object Cluster extends DeployMode

  implicit val deployModeShow: Show[DeployMode] =
    Show.show {
      case Client  => "client"
      case Cluster => "cluster"
    }
}
