package io.github.mjcramer.binance.spot.market

import com.binance.connector.client.SpotClient
import com.binance.connector.client.impl.spot.Market as WrappedMarket
import com.typesafe.scalalogging.LazyLogging
import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import io.github.mjcramer.binance.spot.{BinanceSpotClient, SpotApiParameters, SpotApiRequest}
import io.github.mjcramer.binance.spot.enums.{KlineInterval}
import zio.{IO, Task, Trace, URLayer, ZIO, ZLayer}

import java.util
import scala.jdk.CollectionConverters.*


case class Market(protected val wrappedMarket: WrappedMarket) extends LazyLogging {

  private def spotApi[SpotApiResponse](spotApiCall: SpotApiRequest)
                                               (implicit decoder: Decoder[SpotApiResponse]): Task[SpotApiResponse] = ZIO.fromEither {
    parse(spotApiCall(new SpotApiParameters())).flatMap { json =>
      logger.debug(json.toString)
      json.as[SpotApiResponse]
    }
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Test connectivity to the Rest API.
    *
    * @return
    */
  def ping(): Task[Json] = ZIO.fromEither {
    parse(wrappedMarket.ping())
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Test connectivity to the Rest API and get the current server time.
    *
    * @return
    */
  def time(): Task[Long] = ZIO.fromEither {
    parse(wrappedMarket.time()).flatMap { json =>
      json.hcursor.get[Long]("serverTime")
    }
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Current exchange trading rules and symbol information
    *
    * @return
    */
  def exchangeInfo(symbol: String): Task[ExchangeInfo] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    wrappedMarket.exchangeInfo(parameters)
  }

  def exchangeInfo(symbols: List[String]): Task[ExchangeInfo] = spotApi { parameters =>
    parameters.put("symbols", new util.ArrayList(symbols.asJava))
    wrappedMarket.exchangeInfo(parameters)
  }

  def exchangeInfoByPermissions(permissions: List[String]): Task[ExchangeInfo] = spotApi { parameters =>
    parameters.put("permissions", new util.ArrayList(permissions.asJava))
    wrappedMarket.exchangeInfo(parameters)
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Order book depth information
    *
    * @return OrderBook
    */
  def depth(symbol: String, limit: Int = 100): Task[OrderBook] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    parameters.put("limit", limit.asInstanceOf[AnyRef])
    wrappedMarket.depth(parameters)
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Recent trades list
    *
    * @param parameters Contains, the list of query parameters and must be a java linked hashmap because the wrapped
    *                   library requires it
    * @return a `Seq` of `Trade` representing the latest trades for a symbol
    * @param symbol
    * @param limit
    * @return
    */
  def trades(symbol: String, maybeLimit: Option[Int] = None): Task[Seq[Trade]] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    maybeLimit.foreach { limit =>
      parameters.put("limit", limit.asInstanceOf[AnyRef])
    }
    wrappedMarket.trades(parameters)
  }

  def trades(symbol: String, limit: Int): Task[Seq[Trade]] = trades(symbol, Some(limit))

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Compressed/Aggregate trades list.
    *
    * @param parameters Contains, the list of query parameters and must be a java linked hashmap because the wrapped
    *                   library requires it
    * @return Seq[AggregateTrade]
    */
  def aggTrades(symbol: String,
                maybeFromId: Option[Long] = None,
                maybeStartTime: Option[Long] = None,
                maybeEndTime: Option[Long] = None,
                maybeLimit: Option[Int] = None
               ): Task[Seq[AggregateTrade]] = spotApi { parameters =>

    parameters.put("symbol", symbol)
    maybeFromId.foreach { fromId =>
      parameters.put("fromId", fromId.asInstanceOf[AnyRef])
    }
    maybeStartTime.foreach { startTime =>
      parameters.put("startTime", startTime.asInstanceOf[AnyRef])
    }
    maybeEndTime.foreach { endTime =>
      parameters.put("endTime", endTime.asInstanceOf[AnyRef])
    }
    maybeLimit.foreach { limit =>
      parameters.put("limit", limit.asInstanceOf[AnyRef])
    }
    wrappedMarket.aggTrades(parameters)
  }

  def aggTrades(symbol: String, limit: Int): Task[Seq[AggregateTrade]] = aggTrades(symbol, maybeLimit = Some(limit))

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Gets candlestick bars for a symbol. Klines are uniquely identified by their open time.
    *
    * @param symbol
    * @param interval
    * @param maybeStartTime
    * @param maybeEndTime
    * @param maybeLimit
    * @return
    */
  def klines(symbol: String,
             interval: KlineInterval,
             maybeStartTime: Option[Long] = None,
             maybeEndTime: Option[Long] = None,
             maybeLimit: Option[Int] = None,
             uiOptimized: Boolean = false
            ): Task[Seq[Kline]] = spotApi { parameters =>

    parameters.put("symbol", symbol)
    parameters.put("interval", interval.entryName)
    maybeStartTime.foreach { startTime =>
      parameters.put("startTime", startTime.asInstanceOf[AnyRef])
    }
    maybeEndTime.foreach { endTime =>
      parameters.put("endTime", endTime.asInstanceOf[AnyRef])
    }
    maybeLimit.foreach { limit =>
      parameters.put("limit", limit.asInstanceOf[AnyRef])
    }
    if uiOptimized then wrappedMarket.klines(parameters) else wrappedMarket.uiKlines(parameters)
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * Current average price for a symbol.
    */
  def averagePrice(symbol: String): Task[AveragePrice] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    wrappedMarket.averagePrice(parameters)
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    * 24 hour price change statistics.
    */
  def ticker24H(symbol: String): Task[Ticker24Hr] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    parameters.put("type", "FULL")
    wrappedMarket.ticker24H(parameters)
  }

  def ticker24H(symbols: Seq[String]): Task[Seq[Ticker24Hr]] = spotApi { parameters =>
    parameters.put("symbols", new util.ArrayList(symbols.asJava))
    parameters.put("type", "FULL")
    wrappedMarket.ticker24H(parameters)
  }

  def ticker24HMini(symbol: String): Task[TickerMini] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    parameters.put("type", "MINI")
    wrappedMarket.ticker24H(parameters)
  }

  def ticker24HMini(symbols: Seq[String]): Task[Seq[TickerMini]] = spotApi { parameters =>
    parameters.put("symbols", new util.ArrayList(symbols.asJava))
    parameters.put("type", "MINI")
    wrappedMarket.ticker24H(parameters)
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *
    * @param symbol
    * @return
    */
  def ticker(symbol: String, windowSize: Option[String] = None): Task[TickerRollingWindow] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    windowSize.foreach { size =>
      parameters.put("windowSize", size)
    }
    parameters.put("type", "FULL")
    wrappedMarket.ticker(parameters)
  }

  def tickerMini(symbol: String, windowSize: Option[String] = None): Task[TickerMini] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    windowSize.foreach { size =>
      parameters.put("windowSize", size)
    }
    parameters.put("type", "MINI")
    wrappedMarket.ticker(parameters)
  }

  def ticker(symbols: Seq[String], windowSize: Option[String]): Task[Seq[TickerRollingWindow]] = spotApi { parameters =>
    parameters.put("symbols", new util.ArrayList(symbols.asJava))
    windowSize.foreach { size =>
      parameters.put("windowSize", size)
    }
    parameters.put("type", "FULL")
    wrappedMarket.ticker(parameters)
  }

  def tickerMini(symbols: Seq[String], windowSize: Option[String]): Task[Seq[TickerMini]] = spotApi { parameters =>
    parameters.put("symbols", new util.ArrayList(symbols.asJava))
    windowSize.foreach { size =>
      parameters.put("windowSize", size)
    }
    parameters.put("type", "MINI")
    wrappedMarket.ticker(parameters)
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *
    * @param symbol
    * @return
    */
  def tickerPrice(symbol: String): Task[TickerPrice] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    wrappedMarket.tickerSymbol(parameters)
  }

  def tickerPrice(symbols: Seq[String]): Task[Seq[TickerPrice]] = spotApi { parameters =>
    parameters.put("symbols", new util.ArrayList(symbols.asJava))
    wrappedMarket.tickerSymbol(parameters)
  }

  /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *
    * @param payload
    * @return
    */
  def bookTicker(symbol: String): Task[OrderBookTicker] = spotApi { parameters =>
    parameters.put("symbol", symbol)
    wrappedMarket.bookTicker(parameters)
  }

  def bookTicker(symbols: Seq[String]): Task[Seq[OrderBookTicker]] = spotApi { parameters =>
    parameters.put("symbols", new util.ArrayList(symbols.asJava))
    wrappedMarket.bookTicker(parameters)
  }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//
//  private def tickerBookTicker(parameters: util.LinkedHashMap[String, AnyRef]): Task[Seq[TickerBookTicker]] = ZIO.fromEither {
//    parse(wrappedMarket.tickerBookTicker(parameters)).flatMap { json =>
//      json.as[Seq[TickerBookTicker]]
//    }
//  }
//
//  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//
//  private def tickerAllBookTickers(parameters: util.LinkedHashMap[String, AnyRef]): Task[Seq[TickerBookTicker]] = ZIO.fromEither {
//    parse(wrappedMarket.tickerAllBookTickers(parameters)).flatMap { json =>
//      json.as[Seq[TickerBookTicker]]
//    }
//  }

  def ticker(payload: util.LinkedHashMap[String, AnyRef]): Task[Json] = ZIO.attempt {
    wrappedMarket.ticker(payload).asJson
  }
}

object Market {
  def layer: URLayer[BinanceSpotClient, Market] = ZLayer.service[BinanceSpotClient].flatMap { env =>
    ZLayer.succeed(Market(env.get.wrapped.createMarket()))
  }
}
