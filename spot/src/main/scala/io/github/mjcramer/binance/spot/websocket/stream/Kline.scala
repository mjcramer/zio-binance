package io.github.mjcramer.binance.spot.websocket.stream

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, HCursor}


case class Kline(
                  openTime: Long,
                  closeTime: Long,
                  interval: String,
                  firstTradeId: Long,
                  lastTradeId: Long,
                  openPrice: BigDecimal,
                  closePrice: BigDecimal,
                  highPrice: BigDecimal,
                  lowPrice: BigDecimal,
                  baseAssetVolume: BigDecimal,
                  numberOfTrades: Long,
                  isClosed: Boolean,
                  quoteAssetVolume: BigDecimal,
                  takerBuyBaseAssetVolume: BigDecimal,
                  takerBuyQuoteAssetVolume: BigDecimal
                )

object Kline {

  implicit val decoder: Decoder[Kline] = (c: HCursor) => {

    for {
      openTime                 <- c.downField("k").downField("t").as[Long]
      closeTime                <- c.downField("k").downField("T").as[Long]
      interval                 <- c.downField("k").downField("i").as[String]
      firstTradeId             <- c.downField("k").downField("f").as[Long]
      lastTradeId              <- c.downField("k").downField("L").as[Long]
      openPrice                <- c.downField("k").downField("o").as[BigDecimal]
      closePrice               <- c.downField("k").downField("c").as[BigDecimal]
      highPrice                <- c.downField("k").downField("h").as[BigDecimal]
      lowPrice                 <- c.downField("k").downField("l").as[BigDecimal]
      baseAssetVolume          <- c.downField("k").downField("v").as[BigDecimal]
      numberOfTrades           <- c.downField("k").downField("n").as[Long]
      isClosed                 <- c.downField("k").downField("x").as[Boolean]
      quoteAssetVolume         <- c.downField("k").downField("q").as[BigDecimal]
      takerBuyBaseAssetVolume  <- c.downField("k").downField("V").as[BigDecimal]
      takerBuyQuoteAssetVolume <- c.downField("k").downField("Q").as[BigDecimal]
    } yield Kline(
      openTime,
      closeTime,
      interval,
      firstTradeId,
      lastTradeId,
      openPrice,
      closePrice,
      highPrice,
      lowPrice,
      baseAssetVolume,
      numberOfTrades,
      isClosed,
      quoteAssetVolume,
      takerBuyBaseAssetVolume,
      takerBuyQuoteAssetVolume
    )
  }
  implicit val seqDecoder: Decoder[Seq[Kline]] = Decoder.decodeList[Kline].map(_.toSeq)
}
