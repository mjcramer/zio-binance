package io.github.mjcramer.binance.examples

import io.github.mjcramer.binance.spot.websocket.stream.BinanceWebsocketStreamClient
import zio.*
import zio.logging.backend.SLF4J
import zio.stream.{ ZSink, ZStream }

object ZioBinanceStreamExample extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = Runtime.removeDefaultLoggers >>> SLF4J.slf4j

  val BTCUSDT = "BTCUSDT"
  val BNBBTC  = "BNBBTC"

  val updateSymbolState: ZIO[Any, Nothing, Unit] = ZIO.succeed(())


  val app: ZIO[BinanceWebsocketStreamClient, Throwable, Unit] = ZIO.scoped {
    for {
      client <- ZIO.service[BinanceWebsocketStreamClient]
      symbols <- Ref.make(Set.empty[(String,Double)])
      fiber <- client.allTickerStream()
        .tap { kline =>
          ZIO.logInfo(s"Received kline for ${kline.symbol} ${kline.eventTime}")
        }


        //      client <- ZIO.service[BinanceWebsocketStreamClient]
//      _ <- ZStream.mergeAllUnbounded(
//          client.aggTradeStream(BTCUSDT),
//          client.tradeStream(BTCUSDT),
//          client.klineStream(BTCUSDT, "1m"),
//          client.tickerStream(BTCUSDT),
//          client.bookTickerStream(BTCUSDT),
//          client.partialBookDepthStream(BTCUSDT),
//          client.diffDepthStream(BTCUSDT),
//          client.partialDepthStream(BTCUSDT)
//        )
        .run(ZSink.drain)
        .onInterrupt(ZIO.logInfo("Interrupted"))
        .fork
      exit <- fiber.await
//        .onInterrupt {
//            ZIO.logInfo("Interrupted")
//        }
    } yield ()
  }



  override def run: URIO[Any, ExitCode] = app.provide(BinanceWebsocketStreamClient.testLayer)
    .ensuring(ZIO.logInfo("Ensuring exit"))
    .exitCode
}
