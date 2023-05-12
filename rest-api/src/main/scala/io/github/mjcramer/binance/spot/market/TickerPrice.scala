package io.github.mjcramer.binance.spot.market

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder, HCursor}


case class TickerPrice(symbol: String, price: BigDecimal)


object TickerPrice {

  implicit val encoder: Encoder[TickerPrice] = deriveEncoder
  implicit val decoder: Decoder[TickerPrice] = deriveDecoder
  implicit val seqDecoder: Decoder[Seq[TickerPrice]] = Decoder.decodeSeq[TickerPrice]
}
