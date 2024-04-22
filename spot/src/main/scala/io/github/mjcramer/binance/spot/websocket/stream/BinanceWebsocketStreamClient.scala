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

  private def parseAsJson[E: Decoder](data: String) = ZIO.fromEither {
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

  def miniTickerStream(symbol: String): ZStream[Any, Throwable, Trade] =
    ZStream.async[Any, Throwable, Trade] { callback =>
      wrapped.miniTickerStream(
        symbol,
        (data: String) => callback(logOpenStream(s"Trade stream opened for symbol $symbol.")),
        (data: String) => callback(parseAsJson[Trade](data)),
        (data: String) => callback(logCloseStream(s"Trade stream closed for symbol $symbol.")),
        (data: String) => callback(logFailedStream(s"Trade stream failed for symbol $symbol with error: $data"))
      )
    }


  def symbolTicker(symbol: String): ZStream[Any, Throwable, Trade] =
    ZStream.async[Any, Throwable, Trade] { callback =>
      wrapped.symbolTicker(
        symbol,
        (data: String) => callback(logOpenStream(s"Trade stream opened for symbol $symbol.")),
        (data: String) => callback(parseAsJson[Trade](data)),
        (data: String) => callback(logCloseStream(s"Trade stream closed for symbol $symbol.")),
        (data: String) => callback(logFailedStream(s"Trade stream failed for symbol $symbol with error: $data"))
      )
    }

  def allTickerStream(): ZStream[Any, Throwable, Kline] =
    ZStream.async[Any, Throwable, List[Kline]] { callback =>
      wrapped.allTickerStream(
        (data: String) => callback(logOpenStream(s"Ticker stream for all symbols opened.")),
        (data: String) => callback(parseAsJson[List[Kline]](data)),
        (data: String) => callback(logCloseStream(s"Ticker stream for all symbols closed.")),
        (data: String) => callback(logFailedStream(s"Ticker stream for all symbols failed with error: $data"))
      )
    }.flatMap(ZStream.fromIterable) // flatten the list


  def rollingWindowTicker(symbol: String, windowSize: String): ZStream[Any, Throwable, Kline] =
    ZStream.async[Any, Throwable, Kline] { callback =>
      wrapped.rollingWindowTicker(
        symbol, windowSize,
        (data: String) => callback(logOpenStream(s"Rolling window $windowSize ticker for symbol $symbol is open.")),
        (data: String) => callback(parseAsJson[Kline](data)),
        (data: String) => callback(logCloseStream(s"Rolling window $windowSize for symbol $symbol has closed.")),
        (data: String) => callback(logFailedStream(s"Rolling window $windowSize ticker for symbol $symbol failed with error: $data"))
      )
    }

  def allRollingWindowTicker(windowSize: String): ZStream[Any, Throwable, Kline] =
    ZStream.async[Any, Throwable, Kline] { callback =>
      wrapped.allRollingWindowTicker(
        windowSize,
        (data: String) => callback(logOpenStream(s"Rolling window $windowSize ticker for all symbols is open.")),
        (data: String) => callback(parseAsJson[Kline](data)),
        (data: String) => callback(logCloseStream(s"Rolling window $windowSize for all symbols has closed.")),
        (data: String) => callback(logFailedStream(s"Rolling window $windowSize ticker for all symbols failed with error: $data"))
      )
    }


  def bookTicker(symbol: String): ZStream[Any, Throwable, Trade] =
    ZStream.async[Any, Throwable, Trade] { callback =>
      wrapped.bookTicker(
        symbol,
        (data: String) => callback(logOpenStream(s"Trade stream opened for symbol $symbol.")),
        (data: String) => callback(parseAsJson[Trade](data)),
        (data: String) => callback(logCloseStream(s"Trade stream closed for symbol $symbol.")),
        (data: String) => callback(logFailedStream(s"Trade stream failed for symbol $symbol with error: $data"))
      )
    }

  def partialDepthStream(symbol: String, levels: Int, speed: Int): ZStream[Any, Throwable, Kline] =
    ZStream.async[Any, Throwable, Kline] { callback =>
      wrapped.partialDepthStream(
        symbol, levels, speed,
        (data: String) => callback(logOpenStream(s"Kline stream opened for symbol $symbol.")),
        (data: String) => callback(parseAsJson[Kline](data)),
        (data: String) => callback(logCloseStream(s"Kline stream closed for symbol $symbol.")),
        (data: String) => callback(logFailedStream(s"Kline stream failed for symbol $symbol with error: $data"))
      )
    }

  def diffDepthStream(symbol: String, speed: Int): ZStream[Any, Throwable, Kline] =
    ZStream.async[Any, Throwable, Kline] { callback =>
      wrapped.diffDepthStream(
        symbol, speed,
        (data: String) => callback(logOpenStream(s"Kline stream opened for symbol $symbol.")),
        (data: String) => callback(parseAsJson[Kline](data)),
        (data: String) => callback(logCloseStream(s"Kline stream closed for symbol $symbol.")),
        (data: String) => callback(logFailedStream(s"Kline stream failed for symbol $symbol with error: $data"))
      )
    }


  def listenUserStream(listenKey: String): ZStream[Any, Throwable, Kline] =
    ZStream.async[Any, Throwable, Kline] { callback =>
      wrapped.listenUserStream(
        listenKey,
        (data: String) => callback(logOpenStream(s"Listen user stream for key $listenKey is open.")),
        (data: String) => callback(parseAsJson[Kline](data)),
        (data: String) => callback(logCloseStream(s"Listen user stream for key $listenKey has closed.")),
        (data: String) => callback(logFailedStream(s"Listen user stream for key $listenKey failed with error: $data"))
      )
    }

}

object BinanceWebsocketStreamClient {

  lazy val layer: ULayer[BinanceWebsocketStreamClient] =
    ZLayer.succeed(BinanceWebsocketStreamClient(WebsocketStreamClientImpl()))

  lazy val testLayer: ULayer[BinanceWebsocketStreamClient] =
    ZLayer.succeed(BinanceWebsocketStreamClient(WebsocketStreamClientImpl(DefaultUrls.TESTNET_WSS_URL)))
}
