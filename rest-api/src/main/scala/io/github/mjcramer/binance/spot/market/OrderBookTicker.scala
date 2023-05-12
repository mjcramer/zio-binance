package io.github.mjcramer.binance.spot.market

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class OrderBookTicker(
  symbol: String,
  bidPrice: BigDecimal,
  bidQty: BigDecimal,
  askPrice: BigDecimal,
  askQty: BigDecimal
)

object OrderBookTicker {
  implicit val encoder: Encoder[OrderBookTicker] = deriveEncoder[OrderBookTicker]
  implicit val decoder: Decoder[OrderBookTicker] = deriveDecoder
  implicit val seqDecoder: Decoder[Seq[OrderBookTicker]] = Decoder.decodeSeq[OrderBookTicker]
}
