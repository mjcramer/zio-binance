package io.github.mjcramer.binance.spot.websocket.stream

import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.{ Decoder, HCursor }

case class Kline(
    eventType: String,
    eventTime: Long,
    symbol: String,
    priceChange: Double,
    priceChangePercent: Double,
    weightedAveragePrice: Double,
    firstTradeBefore24HourWindow: Double,
    lastPrice: Double,
    lastQuantity: Double,
    bestBidPrice: Double,
    bestBidQuantity: Double,
    bestAskPrice: Double,
    bestAskQuantity: Double,
    openPrice: Double,
    highPrice: Double,
    lowPrice: Double,
    totalTradedBaseAssetVolume: Double,
    totalTradedQuoteAssetVolume: Double,
    statisticsOpenTime: Long,
    statisticsCloseTime: Long,
    firstTradeId: Long,
    lastTradeId: Long,
    totalNumberOfTrades: Long
)

object Kline {
  implicit val decoder: Decoder[Kline] = (c: HCursor) =>
    for {
      eventType                    <- c.downField("e").as[String]
      eventTime                    <- c.downField("E").as[Long]
      symbol                       <- c.downField("s").as[String]
      priceChange                  <- c.downField("p").as[Double]
      priceChangePercent           <- c.downField("P").as[Double]
      weightedAveragePrice         <- c.downField("w").as[Double]
      firstTradeBefore24HourWindow <- c.downField("x").as[Double]
      lastPrice                    <- c.downField("c").as[Double]
      lastQuantity                 <- c.downField("Q").as[Double]
      bestBidPrice                 <- c.downField("b").as[Double]
      bestBidQuantity              <- c.downField("B").as[Double]
      bestAskPrice                 <- c.downField("a").as[Double]
      bestAskQuantity              <- c.downField("A").as[Double]
      openPrice                    <- c.downField("o").as[Double]
      highPrice                    <- c.downField("h").as[Double]
      lowPrice                     <- c.downField("l").as[Double]
      totalTradedBaseAssetVolume   <- c.downField("v").as[Double]
      totalTradedQuoteAssetVolume  <- c.downField("q").as[Double]
      statisticsOpenTime           <- c.downField("O").as[Long]
      statisticsCloseTime          <- c.downField("C").as[Long]
      firstTradeId                 <- c.downField("F").as[Long]
      lastTradeId                  <- c.downField("L").as[Long]
      totalNumberOfTrades          <- c.downField("n").as[Long]
    } yield Kline(
      eventType,
      eventTime,
      symbol,
      priceChange,
      priceChangePercent,
      weightedAveragePrice,
      firstTradeBefore24HourWindow,
      lastPrice,
      lastQuantity,
      bestBidPrice,
      bestBidQuantity,
      bestAskPrice,
      bestAskQuantity,
      openPrice,
      highPrice,
      lowPrice,
      totalTradedBaseAssetVolume,
      totalTradedQuoteAssetVolume,
      statisticsOpenTime,
      statisticsCloseTime,
      firstTradeId,
      lastTradeId,
      totalNumberOfTrades
    )
  implicit val seqDecoder: Decoder[Seq[Kline]] = Decoder.decodeList[Kline].map(_.toSeq)
}

//
//  "e": "24hrTicker"
//"E": 1696353743275
//"s": "XMRBNB"
//"p": "0.00520000"
//"P": "0.760"
//"w": "0.68409604"
//"x": "0.68430000"
//"c": "0.68900000"
//"Q": "0.68800000"
//"b": "0.68690000"
//"B": "1.39800000"
//"a": "0.68870000"
//"A": "0.87200000"
//"o": "0.68380000"
//"h": "0.68900000"
//"l": "0.68070000"
//"v": "4.94400000"
//"q": "3.38217080"
//"O": 1696267343275
//"C": 1696353743275
//"F": 531
//"L": 536
//"n": 6
