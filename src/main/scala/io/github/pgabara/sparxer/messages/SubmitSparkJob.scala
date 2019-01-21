package io.github.pgabara.sparxer.messages

final case class SubmitSparkJob(metadata: SparkJobMetadata,
                                mainClass: String,
                                master: Master,
                                deployMode: DeployMode,
                                pathToJar: String,
                                args: List[String],
                                sparkConf: Map[String, String])

final case class SparkJobMetadata(id: String, name: String)
