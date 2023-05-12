package io.github.mjcramer.binance.spot

import com.binance.connector.client.enums.DefaultUrls

case class BinanceSpotClientConfig(
    apiKey: String,
    secretKey: String,
    baseUrl: String
)

object BinanceSpotClientConfig {


  def apply(apiKey: String, secretKey: String): BinanceSpotClientConfig =
    BinanceSpotClientConfig(apiKey, secretKey, DefaultUrls.TESTNET_URL)

  val testing = BinanceSpotClientConfig(TESTNET_API_KEY, TESTNET_SECRET_KEY)
}
