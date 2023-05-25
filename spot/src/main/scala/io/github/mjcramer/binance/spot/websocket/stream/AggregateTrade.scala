package io.github.mjcramer.binance.spot.websocket.stream

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import io.github.mjcramer.binance.spot.api.{AggregateTrade => AggregateTradeApi}

case class AggregateTrade(
  tradeId: Long,
  price: BigDecimal,
  quantity: BigDecimal,
  firstTradeId: Long,
  lastTradeId: Long,
  timestamp: Long,
  buyerIsMaker: Boolean
) extends AggregateTradeApi

object AggregateTrade {

  implicit val decoder: Decoder[AggregateTrade] = Decoder.forProduct7(
    "a", // aggregate tradeId
    "p", // price
    "q", // quantity
    "f", // first trade id
    "l", // last trade id
    "T", // timestamp
    "m", // if buyer is the maker
  )(AggregateTrade.apply)
  implicit val seqDecoder: Decoder[Seq[AggregateTrade]] = Decoder.decodeList[AggregateTrade].map(_.toSeq)

  implicit val encoder: Encoder[AggregateTrade] = Encoder.forProduct7(
    "a", // aggregate tradeId
    "p", // price
    "q", // quantity
    "f", // first trade id
    "l", // last trade id
    "T", // timestamp
    "m", // if buyer is the maker
  ) { aggregateTrade =>
    (
      aggregateTrade.tradeId,
      aggregateTrade.price,
      aggregateTrade.quantity,
      aggregateTrade.firstTradeId,
      aggregateTrade.lastTradeId,
      aggregateTrade.timestamp,
      aggregateTrade.buyerIsMaker
    )
  }
}
