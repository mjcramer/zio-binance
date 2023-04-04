package io.github.mjcramer.binance.spot.market

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder, HCursor}


case class DepthPrice(level: BigDecimal, quantity: BigDecimal)


object DepthPrice {

  implicit val encoder: Encoder[DepthPrice] = deriveEncoder
  //  implicit val decoder: Decoder[DepthPrice] = deriveDecoder
  implicit val decoder: Decoder[DepthPrice] = (c: HCursor) => {
    for {
      level    <- c.downArray.as[BigDecimal]
      quantity <- c.downN(1).as[BigDecimal]
    } yield DepthPrice(level, quantity)
  }
}
