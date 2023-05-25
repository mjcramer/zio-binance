package io.github.mjcramer.binance.spot.rest

import io.github.mjcramer.binance.spot.rest.market.MarketSpec
import io.github.mjcramer.binance.spot.rest.{BinanceSpotClient, BinanceSpotClientConfig}
import zio.*
import zio.test.*
import zio.test.Assertion.*
import zio.test.TestAspect.*


object BinanceSpotClientSpec
  extends MarketSpec {

  def spec: Spec[TestEnvironment, Any] =
    suite("Binance Spot Rest Client Tests")(marketSuite)
      .provideShared(
        ZLayer.succeed(BinanceSpotClientConfig.testing),
        BinanceSpotClient.testLayer,
    )
}
