package io.github.mjcramer.binance.spot.api

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}


trait AggregateTrade {
  val tradeId: Long
  val price: BigDecimal
  val quantity: BigDecimal
  val firstTradeId: Long
  val lastTradeId: Long
  val timestamp: Long
  val buyerIsMaker: Boolean
  val bestPriceMatch: Option[Boolean] = None
}
