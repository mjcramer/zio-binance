package io.github.mjcramer.binance.spot.market

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

import java.time.Instant

case class ExchangeInfo(
  timezone: String,
  serverTime: Long,
  rateLimits: Seq[RateLimit],
  exchangeFilters: Seq[ExchangeFilter],
  symbols: Seq[CurrencySymbol]
)

object ExchangeInfo {
  implicit val decode: Decoder[ExchangeInfo] = deriveDecoder
  implicit val encode: Encoder[ExchangeInfo] = deriveEncoder
}
