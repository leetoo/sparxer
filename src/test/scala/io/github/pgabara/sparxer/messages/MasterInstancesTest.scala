package io.github.pgabara.sparxer.messages

import org.scalatest.{MustMatchers, WordSpec}

import cats.implicits._

class MasterInstancesTest extends WordSpec with MustMatchers {

  import Master._

  "MasterInstances" when {

    "master show instance" should {

      "show Local master with n worker threads and without maxFailures" in {
        val master: Master = Local(Strict(4), none[Int])
        master.show mustBe "local[4]"
      }

      "show Local master with n worker threads and with maxFailures" in {
        val master: Master = Local(Strict(4), 5.some)
        master.show mustBe "local[4,5]"
      }

      "show Local master with as many worker threads as logical cores and without maxFailures" in {
        val master: Master = Local(LogicCoresNumber, none[Int])
        master.show mustBe "local[*]"
      }

      "show Local master with as many worker threads as logical cores and with maxFailures" in {
        val master: Master = Local(LogicCoresNumber, 5.some)
        master.show mustBe "local[*,5]"
      }

      "show Yarn master" in {
        val master: Master = Yarn
        master.show mustBe "yarn"
      }

      "show Spark Standalone Cluster master" in {
        val master: Master = SparkCluster("spark://host:4545")
        master.show mustBe "spark://host:4545"
      }

      "show Mesos master" in {
        val master: Master = Mesos("mesos://host:4545")
        master.show mustBe "mesos://host:4545"
      }

      "show K8s master" in {
        val master: Master = K8s("k8s://host:4545")
        master.show mustBe "k8s://host:4545"
      }
    }
  }
}
