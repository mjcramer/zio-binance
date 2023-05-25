package io.github.mjcramer.binance.spot.rest.market

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, HCursor}


case class Kline(
  openTime: Long,
  openPrice: BigDecimal,
  highPrice: BigDecimal,
  lowPrice: BigDecimal,
  closePrice: BigDecimal,
  volume: BigDecimal,
  closeTime: Long,
  quoteAssetVolume: BigDecimal,
  numberOfTrades: Long,
  takerBuyBaseAssetVolume: BigDecimal,
  takerBuyQuoteAssetVolume: BigDecimal,
  ignore: String)

object Kline {

  implicit val decoder: Decoder[Kline] = (c: HCursor) => {
    for {
      openTime                 <- c.downArray.as[Long]
      openPrice                <- c.downN(1).as[BigDecimal]
      highPrice                <- c.downN(2).as[BigDecimal]
      lowPrice                 <- c.downN(3).as[BigDecimal]
      closePrice               <- c.downN(4).as[BigDecimal]
      volume                   <- c.downN(5).as[BigDecimal]
      closeTime                <- c.downN(6).as[Long]
      quoteAssetVolume         <- c.downN(7).as[BigDecimal]
      numberOfTrades           <- c.downN(8).as[Long]
      takerBuyBaseAssetVolume  <- c.downN(9).as[BigDecimal]
      takerBuyQuoteAssetVolume <- c.downN(10).as[BigDecimal]
      ignore                   <- c.downN(11).as[String]
    } yield Kline(
        openTime,
        openPrice,
        highPrice,
        lowPrice,
        closePrice,
        volume,
        closeTime,
        quoteAssetVolume,
        numberOfTrades,
        takerBuyBaseAssetVolume,
        takerBuyQuoteAssetVolume,
        ignore
      )
  }
  implicit val seqDecoder: Decoder[Seq[Kline]] = Decoder.decodeList[Kline].map(_.toSeq)
}
