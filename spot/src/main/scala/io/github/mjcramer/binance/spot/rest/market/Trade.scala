package io.github.mjcramer.binance.spot.rest.market

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.deriveDecoder
import io.github.mjcramer.binance.spot.api.Trade as TradeApi

case class Trade(
    id: Long,
    price: BigDecimal,
    quantity: BigDecimal,
    quoteQuantity: BigDecimal,
    time: Long,
    isBuyerMarketMaker: Boolean,
    isBestPriceMatch: Boolean
) extends TradeApi

object Trade {
  implicit val decoder: Decoder[Trade] = Decoder.forProduct7(
    "id",
    "price",
    "qty",
    "quoteQty",
    "time",
    "isBuyerMaker",
    "isBestMatch"
  )(Trade.apply)
  implicit val seqDecoder: Decoder[Seq[Trade]] = Decoder.decodeList[Trade].map(_.toSeq)
  implicit val encoder: Encoder[Trade] = Encoder.forProduct7(
    "id",
    "price",
    "qty",
    "quoteQty",
    "time",
    "isBuyerMaker",
    "isBestMatch"
  ) { trade =>
    (
      trade.id,
      trade.price,
      trade.quantity,
      trade.quoteQuantity,
      trade.time,
      trade.isBuyerMarketMaker,
      trade.isBestPriceMatch
    )
  }
}

