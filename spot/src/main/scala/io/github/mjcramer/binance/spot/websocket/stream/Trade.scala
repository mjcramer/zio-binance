package io.github.mjcramer.binance.spot.websocket.stream

import io.circe.Decoder
import java.time.Instant
import io.github.mjcramer.binance.spot.api.Trade as TradeApi

case class Trade(
  eventTime: Long,
  symbol: String,
  id: Long,
  price: BigDecimal,
  quantity: BigDecimal,
  buyerOrderId: Long,
  sellerOrderId: Long,
  time: Long,
  isBuyerMarketMaker: Boolean
) //extends TradeApi

object Trade {

  implicit val decoder: Decoder[Trade] = Decoder.forProduct9(
    "E",
    "s",
    "t",
    "p",
    "q",
    "b",
    "a",
    "T",
    "m"
  )(Trade.apply)
  implicit val seqDecoder: Decoder[Seq[Trade]] = Decoder.decodeList[Trade].map(_.toSeq)
}
