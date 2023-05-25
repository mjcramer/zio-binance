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


  val testing: BinanceSpotClientConfig = {
    for {
      apiKey <- sys.env.get("BINANCE_API_KEY")
      secretKey <- sys.env.get("BINANCE_SECRET_KEY")
    } yield BinanceSpotClientConfig(apiKey, secretKey, DefaultUrls.TESTNET_URL)
  }.getOrElse(throw new RuntimeException("Missing Binance API key and/or secret key"))

}
