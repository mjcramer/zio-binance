package io.github.mjcramer.binance

import java.util

package object spot {

  /**
    * The underlying java type of Binance Spot Client parameters
    */
  type SpotApiParameters = util.LinkedHashMap[String, AnyRef]
  
  /**
    * A typical Binance API call, which takes a map of parameters and returns a JSON string.
    * These types match the underlying Java API calls and are used as convenience for specifying type
    */
  type SpotApiRequest = util.LinkedHashMap[String, AnyRef] => String
  
}
