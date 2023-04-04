package io.github.mjcramer.binance.spot

import zio._
import zio.test._
import zio.test.TestAspect.{fibers, silentLogging, tag, timeout}

import java.util.UUID

trait BaseSpec extends ZIOSpecDefault {

  override def aspects: Chunk[TestAspectAtLeastR[Live]] =
    Chunk(fibers, silentLogging, timeout(10.seconds))

  final val genPatternOption: Gen[Any, Option[String]] =
    Gen.option(Gen.constSample(Sample.noShrink("*")))

  final val uuid: UIO[String] =
    ZIO.succeed(UUID.randomUUID().toString)

}

object BaseSpec {
}