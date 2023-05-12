//package name.cramer.seer.binance.rest.response.order
//
//import com.deliganli.binance4s.common.consts.{OrderSide, OrderStatus, OrderType, TimeInForce}
//import io.circe.Decoder
//import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
//case class CancelOrder(
//  symbol: String,
//  origClientOrderId: String,
//  orderId: Long,
//  orderListId: Long,
//  clientOrderId: Long,
//  price: BigDecimal,
//  origQty: BigDecimal,
//  executedQty: BigDecimal,
//  cummulativeQuoteQty: BigDecimal,
//  status: OrderStatus,
//  timeInForce: TimeInForce,
//  orderType: OrderType,
//  side: OrderSide)
//
//object CancelOrder {
//
//  implicit val cancelOrderDecoder = Decoder.forProduct13(
//    "symbol",
//    "origClientOrderId",
//    "orderId",
//    "orderListId",
//    "clientOrderId",
//    "price",
//    "origQty",
//    "executedQty",
//    "cummulativeQuoteQty",
//    "status",
//    "timeInForce",
//    "type",
//    "side"
//  )(CancelOrder.apply)
//}
