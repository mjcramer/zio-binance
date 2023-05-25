package io.github.mjcramer.binance.spot

import zio.*
import zio.test.*
import zio.test.TestAspect.{fibers, silentLogging, tag, timeout}

import java.util.UUID

trait BaseSpec extends ZIOSpecDefault {

  object Symbols {
    val BITCOIN_US = "BTCUSDT"
  }

  override def aspects: Chunk[TestAspectAtLeastR[Live]] =
    Chunk(fibers, silentLogging, timeout(10.seconds))

}

object BaseSpec {
}