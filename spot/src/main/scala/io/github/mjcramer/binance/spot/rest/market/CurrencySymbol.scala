package io.github.mjcramer.binance.spot.rest.market

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import io.github.mjcramer.binance.spot.api.enums.{OrderType, SymbolStatus}


case class CurrencySymbol(
  symbol: String,
  status: SymbolStatus,
  baseAsset: String,
  baseAssetPrecision: Int,
  quoteAsset: String,
  quotePrecision: Int,
  baseCommissionPrecision: Int,
  quoteCommissionPrecision: Int,
  orderTypes: Seq[OrderType],
  icebergAllowed: Boolean,
  ocoAllowed: Boolean,
  quoteOrderQtyMarketAllowed: Boolean,
  allowTrailingStop: Boolean,
  cancelReplaceAllowed: Boolean,
  isSpotTradingAllowed: Boolean,
  isMarginTradingAllowed: Boolean,
  filters: Seq[SymbolFilter],
  permissions: Seq[String],
  defaultSelfTradePreventionMode: String,
  allowedSelfTradePreventionModes: Seq[String]
    //"NONE"
    //"EXPIRE_TAKER"
    //"EXPIRE_MAKER"
    //"EXPIRE_BOTH"
)

object CurrencySymbol {
  implicit val decoder: Decoder[CurrencySymbol] = deriveDecoder[CurrencySymbol]
  implicit val encoder: Encoder[CurrencySymbol] = deriveEncoder[CurrencySymbol]
}
