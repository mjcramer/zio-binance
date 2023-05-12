//package name.cramer.seer.consts
//
//import enumeratum.{CirceEnum, Enum, EnumEntry}
//import enumeratum.EnumEntry.UpperSnakecase
//
//sealed trait OCOOrderStatus extends EnumEntry
//
//object OCOOrderStatus extends Enum[OCOOrderStatus] with CirceEnum[OCOOrderStatus] {
//  case object Executing extends OCOOrderStatus with UpperSnakecase
//  case object AllDone   extends OCOOrderStatus with UpperSnakecase
//  case object Reject    extends OCOOrderStatus with UpperSnakecase
//
//  val values = findValues
//}
