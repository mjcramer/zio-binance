package io.github.mjcramer.binance.spot.websocket.stream

import io.github.mjcramer.binance.spot.BaseSpec
import zio.ZIO
import zio.durationInt
import zio.stream.*
import zio.test.*
import zio.test.Assertion.*
import zio.test.TestAspect.*


object BinanceWebsocketStreamClientSpec extends BaseSpec {

  val numberToTake = 5

  def spec: Spec[TestEnvironment, Any] =
    suite("Binance Spot Websocket Client Tests")(
      test(s"Check that we can pull $numberToTake aggregate trades off the stream") {
        for {
          client <- ZIO.service[BinanceWebsocketStreamClient]
          result <- client.aggTradeStream(Symbols.BITCOIN_US).run(ZSink.take(numberToTake))
        } yield assertTrue(
          result.size == numberToTake,
          result.forall(_.tradeId > 0L)
        )
      } @@ TestAspect.timeout(30.seconds),

      test(s"Check that we can pull $numberToTake trades off the stream") {
        for {
          client <- ZIO.service[BinanceWebsocketStreamClient]
          result <- client.tradeStream(Symbols.BITCOIN_US).run(ZSink.take(numberToTake))
        } yield assertTrue(
          result.size == numberToTake,
          result.forall(_.id > 0L)
        )
      } @@ TestAspect.timeout(30.seconds),

      test(s"Check that we can pull $numberToTake Klines off the stream") {
        for {
          client <- ZIO.service[BinanceWebsocketStreamClient]
          result <- client.klineStream(Symbols.BITCOIN_US, "1s").run(ZSink.take(numberToTake))
        } yield assertTrue(
          result.size == numberToTake,
          result.forall(_.openTime > 0L)
        )
      } @@ TestAspect.timeout(30.seconds),

    ).provideShared(BinanceWebsocketStreamClient.testLayer) @@ TestAspect.timeout(30.seconds)
}
