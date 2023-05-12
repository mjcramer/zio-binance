package io.github.mjcramer.binance.spot

import io.github.mjcramer.binance.spot.market.{Market, MarketSpec}
import zio._
import zio.test.*
import zio.test.Assertion._
import zio.test.TestAspect._



object BinanceSpotClientSpec
  extends MarketSpec {


//  def spec = suite("Binance Spot Client API")(
//    test("Get exchange information from the Binance API") {
//      for {
//        client <- BinanceSpotClient.live
//        market <- client.market
//        fiber <- ZIO.sleep(5.minutes).timeout(1.minute).fork
//        _ <- TestClock.adjust(1.minute)
//        result <- fiber.join
//      } yield assertTrue(result.isEmpty)
//    }
//  )

  def spec: Spec[TestEnvironment, Any] =
    suite("Binance Spot Client Tests")(marketSuite)
      .provideShared(
        ZLayer.succeed(BinanceSpotClientConfig.testing),
        BinanceSpotClient.testLayer,
    )
}




