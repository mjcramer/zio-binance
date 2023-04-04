package io.github.mjcramer.binance.spot.market

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}


case class RateLimit(
  rateLimitType: String,
  interval: String,
  intervalNum: Long,
  limit: Long
)

object RateLimit {
  implicit val decode: Decoder[RateLimit] = deriveDecoder
  implicit val encode: Encoder[RateLimit] = deriveEncoder
}
