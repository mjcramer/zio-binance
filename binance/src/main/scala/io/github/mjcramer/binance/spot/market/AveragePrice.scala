package io.github.mjcramer.binance.spot.market

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class AveragePrice(
  mins: Int,
  price: BigDecimal
)

object AveragePrice {
  implicit val decoder: Decoder[AveragePrice] = deriveDecoder
  implicit val encoder: Encoder[AveragePrice] = deriveEncoder
}
