package io.github.mjcramer.binance.spot.market

import io.circe.Json
import io.github.mjcramer.binance.spot.{BaseSpec, BinanceSpotClient}
import io.github.mjcramer.binance.spot.enums.KlineInterval
import zio.ZIO
import zio.test.{Spec, assertTrue}


trait MarketSpec extends BaseSpec {

  val marketSuite: Spec[BinanceSpotClient, Throwable] = suite("Market API Tests")(

    test("Check that we can ping the server") {
      for {
        market <- ZIO.service[Market]
        result <- market.ping()
      } yield assertTrue(result.asObject.isDefined)
    },

    test("Check the servers time is current") {
      for {
        market     <- ZIO.service[Market]
        before <- market.time()
        after <- ZIO.succeed(System.currentTimeMillis())
      } yield assertTrue(before <  after)
    },

    test("Get exchange information") {
      for {
        market <- ZIO.service[Market]
        info1  <- market.exchangeInfo("BNBUSDT")
        info2  <- market.exchangeInfo(List("BTCUSDT", "BNBBTC"))
        info3  <- market.exchangeInfoByPermissions(List("SPOT"))
      } yield assertTrue(
        info1.symbols.exists(_.symbol == "BNBUSDT"),
        info2.symbols.exists(_.symbol == "BTCUSDT"),
        info2.symbols.exists(_.symbol == "BNBBTC"),
        info3.symbols.exists(_.permissions.contains("SPOT"))
      )
    },

    test("Get order book") {
      for {
        market <- ZIO.service[Market]
        order1 <- market.depth("BNBUSDT")
        order2 <- market.depth("BNBUSDT", 1)
      } yield assertTrue(
        order1.lastUpdateId > 0l,
        order2.bids.size == 1,
        order2.asks.size == 1
      )
    },

    test("Get recent trades") {
      for {
        market <- ZIO.service[Market]
        trades1 <- market.trades("BNBUSDT")
        trades2 <- market.trades("BNBUSDT", Some(1))
      } yield assertTrue(
        trades1.nonEmpty,
        trades2.size == 1
      )
    },

    test ("Get aggregate trades") {
      for {
        market <- ZIO.service[Market]
        trades1 <- market.aggTrades("BNBUSDT")
        trades2 <- market.aggTrades("BNBUSDT", 1)
      } yield assertTrue(
        trades1.nonEmpty,
        trades2.size == 1
      )
    },

    test ("Get candlestick data") {
      for {
        market <- ZIO.service[Market]
        klines1 <- market.klines("BNBUSDT", KlineInterval.OneMinute)
        klines2 <- market.klines("BNBUSDT", KlineInterval.OneMinute, maybeLimit = Some(1))
        klines3 <- market.klines("BNBUSDT", KlineInterval.OneMinute, uiOptimized = true)
        klines4 <- market.klines("BNBUSDT", KlineInterval.OneMinute, maybeLimit = Some(1), uiOptimized = true)
      } yield assertTrue(
        klines1.nonEmpty,
        klines2.size == 1,
        klines3.nonEmpty,
        klines4.size == 1
      )
    },

    test ("Get current average price") {
      for {
        market <- ZIO.service[Market]
        price <- market.averagePrice("BNBUSDT")
      } yield assertTrue(
        price.price > 0
      )
    },

    test("Get 24hr ticker price change statistics") {
      for {
        market <- ZIO.service[Market]
        ticker <- market.ticker24H("BNBUSDT")
        tickers <- market.ticker24H(Seq("BNBUSDT","BNBBTC"))
        miniTicker <- market.ticker24HMini("BNBUSDT")
        miniTickers <- market.ticker24HMini(Seq("BNBUSDT", "BNBBTC"))
      } yield assertTrue(
        ticker.symbol == "BNBUSDT",
        tickers.exists(_.symbol == "BNBUSDT") && tickers.exists(_.symbol == "BNBBTC"),
        miniTicker.symbol == "BNBUSDT",
        miniTickers.exists(_.symbol == "BNBUSDT") && tickers.exists(_.symbol == "BNBBTC")
      )
    },

    test("Get rolling window price change statistics") {
      for {
        market <- ZIO.service[Market]
        ticker <- market.ticker("BNBUSDT", Some("1d"))
        tickers <- market.ticker(Seq("BNBUSDT", "BNBBTC"), Some("2d"))
        miniTicker <- market.tickerMini("BNBUSDT", Some("1d"))
        miniTickers <- market.tickerMini(Seq("BNBUSDT", "BNBBTC"), Some("3d"))
      } yield assertTrue(
        ticker.symbol == "BNBUSDT",
        tickers.exists(_.symbol == "BNBUSDT") && tickers.exists(_.symbol == "BNBBTC"),
        miniTicker.symbol == "BNBUSDT",
        miniTickers.exists(_.symbol == "BNBUSDT") && tickers.exists(_.symbol == "BNBBTC")
      )
    },

    test("Get latest price for all symbols") {
      for {
        market <- ZIO.service[Market]
        price <- market.tickerPrice("BNBUSDT")
        prices <- market.tickerPrice(Seq("BNBUSDT", "BNBBTC"))
      } yield assertTrue(
        price.symbol == "BNBUSDT",
        prices.exists(_.symbol == "BNBUSDT") && prices.exists(_.symbol == "BNBBTC")
      )
    },

    test("Get latest price book for all symbols") {
      for {
        market <- ZIO.service[Market]
        ticker <- market.bookTicker("BNBUSDT")
        tickers <- market.bookTicker(Seq("BNBUSDT", "BNBBTC"))
      } yield assertTrue(
        ticker.symbol == "BNBUSDT",
        tickers.exists(_.symbol == "BNBUSDT") && tickers.exists(_.symbol == "BNBBTC")
      )
    }
  ).provideSomeLayer[BinanceSpotClient](Market.layer)
}
