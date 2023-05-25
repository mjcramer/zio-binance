package io.github.mjcramer.binance.spot.rest.market

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class OrderBook(
  lastUpdateId: Long,
  bids: Seq[DepthPrice],
  asks: Seq[DepthPrice]
)

object OrderBook {
  implicit val decoder: Decoder[OrderBook] = deriveDecoder
  implicit val encoder: Encoder[OrderBook] = deriveEncoder
}
