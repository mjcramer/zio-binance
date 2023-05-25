package io.github.mjcramer.binance.spot.websocket.stream

import com.binance.connector.client.WebsocketStreamClient
import com.binance.connector.client.enums.DefaultUrls
import com.binance.connector.client.impl.WebsocketStreamClientImpl
import com.binance.connector.client.utils.WebSocketCallback
import com.typesafe.scalalogging.LazyLogging
import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import zio.stream.ZStream
import zio.{Chunk, IO, Task, ULayer, ZIO, ZLayer}

import java.io.Closeable

case class BinanceWebsocketStreamClient(wrapped: WebsocketStreamClient) extends LazyLogging {

  private def logOpenStream[E](message: String): ZIO[Any, Option[Throwable], Chunk[E]] = ZIO.succeed {
    logger.debug(message)
    Chunk.empty
  }

  private def parseAsJson[E](data: String)(implicit decoder: Decoder[E]) = ZIO.fromEither {
    logger.debug(s"Websocket callback received data: $data")
    parse(data).flatMap { json =>
      logger.trace(json.toString)
      json.as[E].map(Chunk(_))
    }
  }.mapError { error =>
    logger.error(error.toString)
    Some(error)
  }

  private def logCloseStream[E](message: String): IO[Option[Throwable], E] = ZIO.fail {
    logger.info(message)
    None
  }

  private def logFailedStream[E](data: String) = ZIO.fail {
    logger.error("Stream failed: $data")
    Some(new RuntimeException(data))
  }

  def aggTradeStream(symbol: String): ZStream[Any, Throwable, AggregateTrade] =
    ZStream.async[Any, Throwable, AggregateTrade] { callback =>
      wrapped.aggTradeStream(
        symbol,
        (data: String) => callback(logOpenStream(s"Aggregate trade stream opened for symbol $symbol.")),
        (data: String) => callback(parseAsJson[AggregateTrade](data)),
        (data: String) => callback(logCloseStream(s"Aggregate trade stream closed for symbol $symbol.")),
        (data: String) => callback(logFailedStream(s"Aggregate trade stream failed for symbol $symbol with error: $data"))
      )
    }
  // TODO: We're gonna have to handle server-side disconnects and reconnects
  //  int aggTradeStream(symbol: String, WebSocketCallback onOpenCallback, WebSocketCallback onMessageCallback, WebSocketCallback onClosingCallback, WebSocketCallback onFailureCallback);

  def tradeStream(symbol: String): ZStream[Any, Throwable, Trade] =
    ZStream.async[Any, Throwable, Trade] { callback =>
      wrapped.tradeStream(
        symbol,
        (data: String) => callback(logOpenStream(s"Trade stream opened for symbol $symbol.")),
        (data: String) => callback(parseAsJson[Trade](data)),
        (data: String) => callback(logCloseStream(s"Trade stream closed for symbol $symbol.")),
        (data: String) => callback(logFailedStream(s"Trade stream failed for symbol $symbol with error: $data"))
      )
    }

  def klineStream(symbol: String, interval: String): ZStream[Any, Throwable, Kline] =
    ZStream.async[Any, Throwable, Kline] { callback =>
      wrapped.klineStream(
        symbol, interval,
        (data: String) => callback(logOpenStream(s"Kline stream opened for symbol $symbol.")),
        (data: String) => callback(parseAsJson[Kline](data)),
        (data: String) => callback(logCloseStream(s"Kline stream closed for symbol $symbol.")),
        (data: String) => callback(logFailedStream(s"Kline stream failed for symbol $symbol with error: $data"))
      )
    }


  //    def miniTickerStream(symbol: String) = ???
//    def allMiniTickerStream() = ???
//    def symbolTicker(symbol: String) = ???
//    def allTickerStream() = ???
//    def rollingWindowTicker(symbol: String, windowSize: String) = ???
//    def allRollingWindowTicker(windowSize: String) = ???
//    def bookTicker(symbol: String) = ???
//    def partialDepthStream(symbol: String, levels: Int, speed: Int) = ???
//    def diffDepthStream(symbol: String, speed: Int) = ???
//    def listenUserStream(listenKey: String) = ???

}

object BinanceWebsocketStreamClient {

  lazy val layer: ULayer[BinanceWebsocketStreamClient] =
    ZLayer.succeed(BinanceWebsocketStreamClient(WebsocketStreamClientImpl()))

  lazy val testLayer: ULayer[BinanceWebsocketStreamClient] =
    ZLayer.succeed(BinanceWebsocketStreamClient(WebsocketStreamClientImpl(DefaultUrls.TESTNET_WSS_URL)))
}
