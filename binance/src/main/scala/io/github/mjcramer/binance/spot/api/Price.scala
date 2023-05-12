//package name.cramer.seer.binance.rest.response.market
//
//import io.circe.Decoder
//import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
//case class Price(
//  symbol: String,
//  price: BigDecimal
//)
//
//object Price {
//  implicit val priceDecoder: Decoder[Price] = Decoder.forProduct2(
//    "symbol",
//    "price"
//  )(Price.apply)
//}
