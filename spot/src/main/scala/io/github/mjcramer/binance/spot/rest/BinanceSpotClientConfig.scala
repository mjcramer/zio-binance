package io.github.mjcramer.binance.spot.rest

import com.binance.connector.client.enums.DefaultUrls

case class BinanceSpotClientConfig(
    apiKey: String,
    secretKey: String,
    baseUrl: String
)

object BinanceSpotClientConfig {

  def apply(apiKey: String, secretKey: String): BinanceSpotClientConfig =
    BinanceSpotClientConfig(apiKey, secretKey, DefaultUrls.PROD_URL)


  val testing = BinanceSpotClientConfig(
    sys.env.getOrElse("BINANCE_API_KEY", ""),
    sys.env.getOrElse("BINANCE_SECRET_KEY", ""),
    DefaultUrls.TESTNET_URL
  )
}
