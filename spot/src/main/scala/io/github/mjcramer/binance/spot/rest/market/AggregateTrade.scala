package io.github.mjcramer.binance.spot.rest.market

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import io.github.mjcramer.binance.spot.api.AggregateTrade as AggregateTradeApi


case class AggregateTrade(
    tradeId: Long,
    price: BigDecimal,
    quantity: BigDecimal,
    firstTradeId: Long,
    lastTradeId: Long,
    timestamp: Long,
    buyerIsMaker: Boolean,
    override val bestPriceMatch: Option[Boolean]
) extends AggregateTradeApi

object AggregateTrade {

  def apply0(
         tradeId: Long,
         price: BigDecimal,
         quantity: BigDecimal,
         firstTradeId: Long,
         lastTradeId: Long,
         timestamp: Long,
         buyerIsMaker: Boolean,
         bestPriceMatch: Boolean
  ): AggregateTrade = AggregateTrade(tradeId, price, quantity, firstTradeId, lastTradeId, timestamp, buyerIsMaker, Some(bestPriceMatch))

  implicit val decoder: Decoder[AggregateTrade] = Decoder.forProduct8(
    "a", // aggregate tradeId
    "p", // price
    "q", // quantity
    "f", // first trade id
    "l", // last trade id
    "T", // timestamp
    "m", // if buyer is the maker
    "M"  // if the trade was the best price match?
  )(AggregateTrade.apply0)
  implicit val seqDecoder: Decoder[Seq[AggregateTrade]] = Decoder.decodeList[AggregateTrade].map(_.toSeq)

  implicit val encoder: Encoder[AggregateTrade] = Encoder.forProduct8(
    "a", // aggregate tradeId
    "p", // price
    "q", // quantity
    "f", // first trade id
    "l", // last trade id
    "T", // timestamp
    "m", // if buyer is the maker
    "M"  // if the trade was the best price match?
  ) { aggregateTrade =>
    (
      aggregateTrade.tradeId,
      aggregateTrade.price,
      aggregateTrade.quantity,
      aggregateTrade.firstTradeId,
      aggregateTrade.lastTradeId,
      aggregateTrade.timestamp,
      aggregateTrade.buyerIsMaker,
      aggregateTrade.bestPriceMatch
    )
  }
}
