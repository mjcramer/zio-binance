package io.github.mjcramer.binance.spot.api


trait Trade {
  val id: Long
  val price: BigDecimal
  val quantity: BigDecimal
  val quoteQuantity: BigDecimal
  val time: Long
  val isBuyerMarketMaker: Boolean
  val isBestPriceMatch: Boolean
}
