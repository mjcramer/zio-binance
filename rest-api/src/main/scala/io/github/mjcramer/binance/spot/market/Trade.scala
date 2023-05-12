package io.github.mjcramer.binance.spot.market

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder


case class Trade(
    id: Long,
    price: BigDecimal,
    qty: BigDecimal,
    quoteQty: BigDecimal,
    time: Long,
    isBuyerMaker: Boolean,
    isBestMatch: Boolean
)

object Trade {
  implicit val decoder: Decoder[Trade] = deriveDecoder
  implicit val seqDecoder: Decoder[Seq[Trade]] = Decoder.decodeList[Trade].map(_.toSeq)
}
