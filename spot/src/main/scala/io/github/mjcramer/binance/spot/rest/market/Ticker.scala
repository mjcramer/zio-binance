package io.github.mjcramer.binance.spot.rest.market

import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.{ Decoder, Encoder }

sealed trait Ticker {
  val symbol: String
  val openPrice: BigDecimal
  val highPrice: BigDecimal
  val lowPrice: BigDecimal
  val lastPrice: BigDecimal
  val volume: BigDecimal
  val quoteVolume: BigDecimal
  val openTime: Long
  val closeTime: Long
  val firstId: Long // First tradeId
  val lastId: Long  // Last tradeId
  val count: Long    // Trade count
}

case class TickerMini(
    symbol: String,
    openPrice: BigDecimal,
    highPrice: BigDecimal,
    lowPrice: BigDecimal,
    lastPrice: BigDecimal,
    volume: BigDecimal,
    quoteVolume: BigDecimal,
    openTime: Long,
    closeTime: Long,
    firstId: Long, // First tradeId
    lastId: Long,  // Last tradeId
    count: Long    // Trade count
) extends Ticker

object TickerMini {
  implicit val decoder: Decoder[TickerMini] = deriveDecoder
  implicit val seqDecoder: Decoder[Seq[TickerMini]] = Decoder.decodeList[TickerMini].map(_.toSeq)
  implicit val encoder: Encoder[TickerMini] = deriveEncoder
}

case class Ticker24Hr(
    symbol: String,
    priceChange: BigDecimal,
    priceChangePercent: BigDecimal,
    weightedAvgPrice: BigDecimal,
    prevClosePrice: BigDecimal,
    lastQty: BigDecimal,
    bidPrice: BigDecimal,
    askPrice: BigDecimal,
    openPrice: BigDecimal,
    highPrice: BigDecimal,
    lowPrice: BigDecimal,
    lastPrice: BigDecimal,
    volume: BigDecimal,
    quoteVolume: BigDecimal,
    openTime: Long,
    closeTime: Long,
    firstId: Long, // First tradeId
    lastId: Long,  // Last tradeId
    count: Long    // Trade count
) extends Ticker


object Ticker24Hr {
  implicit val decoder: Decoder[Ticker24Hr] = deriveDecoder
  implicit val seqDecoder: Decoder[Seq[Ticker24Hr]] = Decoder.decodeSeq[Ticker24Hr]
  implicit val encoder: Encoder[Ticker24Hr] = deriveEncoder
}

case class TickerRollingWindow(
   symbol: String,
   priceChange: BigDecimal,
   priceChangePercent: BigDecimal,
   weightedAvgPrice: BigDecimal,
   openPrice: BigDecimal,
   highPrice: BigDecimal,
   lowPrice: BigDecimal,
   lastPrice: BigDecimal,
   volume: BigDecimal,
   quoteVolume: BigDecimal,
   openTime: Long,
   closeTime: Long,
   firstId: Long, // First tradeId
   lastId: Long,  // Last tradeId
   count: Long    // Trade count
) extends Ticker

object TickerRollingWindow {
  implicit val decoder: Decoder[TickerRollingWindow] = deriveDecoder
  implicit val seqDecoder: Decoder[Seq[TickerRollingWindow]] = Decoder.decodeSeq[TickerRollingWindow]
  implicit val encoder: Encoder[TickerRollingWindow] = deriveEncoder
}
