package io.github.mjcramer.binance.spot.market

import io.circe.generic.semiauto.{ deriveCodec, deriveDecoder, deriveEncoder }
import io.circe.syntax.*
import io.circe.{ Decoder, Encoder, HCursor }

sealed trait SymbolFilter

object SymbolFilter {

  implicit val decoder: Decoder[SymbolFilter] = Decoder.instance { c =>
    c.downField("filterType").as[String].flatMap {
      case PriceFilter.filterType => c.as[PriceFilter]
//      case "PERCENT_PRICE"          => c.as[PercentPriceFilter]
      case PercentPriceBySideFilter.filterType => c.as[PercentPriceBySideFilter]
      case LotSizeFilter.filterType            => c.as[LotSizeFilter]
      case MinNotional.filterType              => c.as[MinNotional]
      case Notional.filterType                 => c.as[Notional]
      case IcebergParts.filterType             => c.as[IcebergParts]
      case MarketLotSize.filterType            => c.as[MarketLotSize]
      case TrailingDelta.filterType            => c.as[TrailingDelta]
      case MaxNumOrders.filterType             => c.as[MaxNumOrders]
      case MaxNumAlgoOrders.filterType         => c.as[MaxNumAlgoOrders]
//      case "MAX_NUM_ICEBERG_ORDERS" => c.as[MaxNumIcebergOrders]
    }
  }

  implicit val encoder: Encoder[SymbolFilter] = Encoder.instance {
    case e: PriceFilter => e.asJson
//    case e: PercentPriceFilter  => e.asJson
    case e: PercentPriceBySideFilter => e.asJson
    case e: LotSizeFilter            => e.asJson
    case e: MinNotional              => e.asJson
    case e: Notional                  => e.asJson
    case e: IcebergParts             => e.asJson
    case e: MarketLotSize            => e.asJson
    case e: TrailingDelta            => e.asJson
    case e: MaxNumOrders             => e.asJson
    case e: MaxNumAlgoOrders         => e.asJson
//    case e: MaxNumIcebergOrders => e.asJson
  }

  case class PriceFilter(
      minPrice: BigDecimal,
      maxPrice: BigDecimal,
      tickSize: Double
  ) extends SymbolFilter

  object PriceFilter {
    val filterType = "PRICE_FILTER"

    implicit val decoder: Decoder[PriceFilter] = deriveDecoder
    implicit val encoder: Encoder[PriceFilter] = Encoder.forProduct4(
      "filterType",
      "minPrice",
      "maxPrice",
      "tickSize"
    ) { priceFilter =>
      (
        PriceFilter.filterType,
        priceFilter.minPrice,
        priceFilter.maxPrice,
        priceFilter.tickSize
      )
    }
  }

//  case class PercentPriceFilter(
//    filterType: String,
//    multiplierUp: Double,
//    multiplierDown: Double,
//    avgPriceMins: Int
//  ) extends SymbolFilter
//
//  object PercentPriceFilter {
//    implicit val decoder: Decoder[PercentPriceFilter] = Decoder.forProduct4(
//      "filterType",
//      "multiplierUp",
//      "multiplierDown",
//      "avgPriceMins"
//    )(PercentPriceFilter.apply)
//
//    implicit val encoder: Encoder[PercentPriceFilter] = Encoder.forProduct4(
//      "filterType",
//      "multiplierUp",
//      "multiplierDown",
//      "avgPriceMins"
//    ) { m =>
//      (
//        m.filterType,
//        m.multiplierUp,
//        m.multiplierDown,
//        m.avgPriceMins
//      )
//    }
//  }

  case class PercentPriceBySideFilter(
      bidMultiplierUp: Double,
      bidMultiplierDown: Double,
      askMultiplierUp: Double,
      askMultiplierDown: Double,
      avgPriceMins: Int
  ) extends SymbolFilter

  object PercentPriceBySideFilter {
    val filterType = "PERCENT_PRICE_BY_SIDE"

    implicit val decoder: Decoder[PercentPriceBySideFilter] = deriveDecoder
    implicit val encoder: Encoder[PercentPriceBySideFilter] = Encoder.forProduct6(
      "filterType",
      "bidMultiplierUp",
      "bidMultiplierDown",
      "askMultiplierUp",
      "askMultiplierDown",
      "avgPriceMins"
    ) { percentPriceBySideFilter =>
      (
        PercentPriceBySideFilter.filterType,
        percentPriceBySideFilter.bidMultiplierUp,
        percentPriceBySideFilter.bidMultiplierDown,
        percentPriceBySideFilter.askMultiplierUp,
        percentPriceBySideFilter.askMultiplierDown,
        percentPriceBySideFilter.avgPriceMins
      )
    }
  }

  case class LotSizeFilter(
      minQty: BigDecimal,
      maxQty: BigDecimal,
      stepSize: Double
  ) extends SymbolFilter

  object LotSizeFilter {
    val filterType = "LOT_SIZE"

    implicit val decoder: Decoder[LotSizeFilter] = deriveDecoder
    implicit val encoder: Encoder[LotSizeFilter] = Encoder.forProduct4(
      "filterType",
      "minQty",
      "maxQty",
      "stepSize"
    ) { lotSizeFilter =>
      (
        LotSizeFilter.filterType,
        lotSizeFilter.minQty,
        lotSizeFilter.maxQty,
        lotSizeFilter.stepSize
      )
    }
  }

  case class MinNotional(
      minNotional: BigDecimal,
      applyToMarket: Boolean,
      avgPriceMins: Int
  ) extends SymbolFilter

  object MinNotional {
    val filterType = "MIN_NOTIONAL"

    implicit val decoder: Decoder[MinNotional] = deriveDecoder
    implicit val encoder: Encoder[MinNotional] = Encoder.forProduct4(
      "filterType",
      "minNotional",
      "applyToMarket",
      "avgPriceMins"
    ) { minNotional =>
      (
        MinNotional.filterType,
        minNotional.minNotional,
        minNotional.applyToMarket,
        minNotional.avgPriceMins
      )
    }
  }

  case class Notional(
      minNotional: BigDecimal,
      applyMinToMarket: Boolean,
      maxNotional: BigDecimal,
      applyMaxToMarket: Boolean,
      avgPriceMins: Int
  ) extends SymbolFilter

  object Notional {
    val filterType = "NOTIONAL"

    implicit val decoder: Decoder[Notional] = deriveDecoder
    implicit val encoder: Encoder[Notional] = Encoder.forProduct6(
      "filterType",
      "minNotional",
      "applyMinToMarket",
      "maxNotional",
      "applyMaxToMarket",
      "avgPriceMins"
    ) { notional =>
      (
        Notional.filterType,
        notional.minNotional,
        notional.applyMinToMarket,
        notional.maxNotional,
        notional.applyMaxToMarket,
        notional.avgPriceMins
      )
    }
  }

  case class IcebergParts(
      limit: Int
  ) extends SymbolFilter

  object IcebergParts {
    val filterType = "ICEBERG_PARTS"

    implicit val decoder: Decoder[IcebergParts] = deriveCodec
    implicit val encoder: Encoder[IcebergParts] = Encoder.forProduct2(
      "filterType",
      "limit"
    ) { m =>
      (
        IcebergParts.filterType,
        m.limit
      )
    }
  }

  case class MarketLotSize(
      minQty: BigDecimal,
      maxQty: BigDecimal,
      stepSize: Double
  ) extends SymbolFilter

  object MarketLotSize {
    val filterType = "MARKET_LOT_SIZE"

    implicit val decoder: Decoder[MarketLotSize] = deriveDecoder
    implicit val encoder: Encoder[MarketLotSize] = Encoder.forProduct4(
      "filterType",
      "minQty",
      "maxQty",
      "stepSize"
    ) { marketLotSize =>
      (
        MarketLotSize.filterType,
        marketLotSize.minQty,
        marketLotSize.maxQty,
        marketLotSize.stepSize
      )
    }
  }

  case class TrailingDelta(
      minTrailingAboveDelta: Int,
      maxTrailingAboveDelta: Int,
      minTrailingBelowDelta: Int,
      maxTrailingBelowDelta: Int
  ) extends SymbolFilter

  object TrailingDelta {
    val filterType = "TRAILING_DELTA"

    implicit val decoder: Decoder[TrailingDelta] = deriveDecoder
    implicit val encoder: Encoder[TrailingDelta] = Encoder.forProduct5(
      "filterType",
      "minTrailingAboveDelta",
      "maxTrailingAboveDelta",
      "minTrailingBelowDelta",
      "maxTrailingBelowDelta"
    ) { trailingDelta =>
      (
        TrailingDelta.filterType,
        trailingDelta.minTrailingAboveDelta,
        trailingDelta.maxTrailingAboveDelta,
        trailingDelta.minTrailingBelowDelta,
        trailingDelta.maxTrailingBelowDelta
      )
    }
  }

  case class MaxNumOrders(
      maxNumOrders: Int
  ) extends SymbolFilter

  object MaxNumOrders {
    val filterType = "MAX_NUM_ORDERS"

    implicit val decoder: Decoder[MaxNumOrders] = deriveDecoder
    implicit val encoder: Encoder[MaxNumOrders] = Encoder.forProduct2(
      "filterType",
      "limit"
    ) { maxNumOrders =>
      (
        MaxNumOrders.filterType,
        maxNumOrders.maxNumOrders
      )
    }
  }

  case class MaxNumAlgoOrders(
      maxNumAlgoOrders: Int
  ) extends SymbolFilter

  object MaxNumAlgoOrders {
    val filterType = "MAX_NUM_ALGO_ORDERS"

    implicit val decoder: Decoder[MaxNumAlgoOrders] = deriveDecoder
    implicit val encoder: Encoder[MaxNumAlgoOrders] = Encoder.forProduct2(
      "filterType",
      "maxNumAlgoOrders"
    ) { maxNumAlgoOrders =>
      (
        MaxNumAlgoOrders.filterType,
        maxNumAlgoOrders.maxNumAlgoOrders
      )
    }
  }

//  case class MaxNumIcebergOrders(
//    filterType: String,
//    maxNumIcebergOrders: Int
//  ) extends SymbolFilter
//
//  object MaxNumIcebergOrders {
//    implicit val decoder: Decoder[MaxNumIcebergOrders] = Decoder.forProduct2(
//      "filterType",
//      "maxNumIcebergOrders"
//    )(MaxNumIcebergOrders.apply)
//
//    implicit val encoder: Encoder[MaxNumIcebergOrders] = Encoder.forProduct2(
//      "filterType",
//      "maxNumIcebergOrders"
//    ) { m =>
//      (
//        m.filterType,
//        m.maxNumIcebergOrders
//      )
//    }
//  }
}
