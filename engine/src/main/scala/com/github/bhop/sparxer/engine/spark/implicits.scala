package com.github.bhop.sparxer.engine.spark

import monix.execution.Cancelable
import monix.reactive.{Observable, OverflowStrategy}
import org.apache.spark.launcher.SparkAppHandle
import scala.util.control.NonFatal

import com.github.bhop.sparxer.protocol.spark.Spark.JobState

object implicits {

  implicit class SparkAppHandleToObservable(sah: SparkAppHandle) {
    def toObservable: Observable[JobState] = {
      Observable.create[JobState](OverflowStrategy.DropOld(bufferSize = 1000)) { subscriber =>
        try {
          sah.addListener(new SparkAppHandle.Listener {
            def infoChanged(handle: SparkAppHandle): Unit = ()
            def stateChanged(handle: SparkAppHandle): Unit = {
              val state = handle.getState
              subscriber.onNext(JobState(Option(handle.getAppId), state.toString))
              if (state.isFinal) subscriber.onComplete()
            }
          })
          Cancelable(() => sah.disconnect())
        } catch {
          case NonFatal(ex) =>
            subscriber.onError(ex)
            Cancelable.empty
        }
      }
    }
  }
}
