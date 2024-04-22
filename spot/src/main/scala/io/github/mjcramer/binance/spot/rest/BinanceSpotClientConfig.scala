package io.github.mjcramer.binance.spot.rest

import com.binance.connector.client.enums.DefaultUrls

case class BinanceSpotClientConfig(
    apiKey: String,
    secretKey: String,
    baseUrl: String
)

object BinanceSpotClientConfig {

  val BINANCE_API_KEY = "BINANCE_API_KEY"
  val BINANCE_SECRET_KEY = "BINANCE_SECRET_KEY"

  val URL = "https://api.binance.com"
  val WSS_URL = "wss://stream.binance.com:9443"
  val WSS_API_URL = "wss://ws-api.binance.com:443/ws-api/v3"
  val TESTNET_URL = "https://testnet.binance.vision"
  val TESTNET_WSS_URL = "wss://testnet.binance.vision"
  val TESTNET_WSS_API_URL = "wss://testnet.binance.vision/ws-api/v3"


  def apply(apiKey: String, secretKey: String): BinanceSpotClientConfig =
    BinanceSpotClientConfig(apiKey, secretKey, DefaultUrls.PROD_URL)

  val testing: BinanceSpotClientConfig = {
    for {
      apiKey <- sys.env.get(BINANCE_API_KEY)
      secretKey <- sys.env.get(BINANCE_SECRET_KEY)
    } yield BinanceSpotClientConfig(apiKey, secretKey, DefaultUrls.TESTNET_URL)
  }.getOrElse(throw new RuntimeException("Missing Binance API key and/or secret key"))
}
