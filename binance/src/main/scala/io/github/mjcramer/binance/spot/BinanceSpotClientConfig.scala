package io.github.mjcramer.binance.spot

import com.binance.connector.client.enums.DefaultUrls

case class BinanceSpotClientConfig(
    apiKey: String,
    secretKey: String,
    baseUrl: String
)

object BinanceSpotClientConfig {
  val API_KEY = "fE9Q8GCZwWaLKBfcxZ1Xa3DtagY8RO0PHXOvUZgL5I2UPfjMTSq4FB2XBJjV9fLo"
  val SECRET_KEY = "sPncbTLZ1dtQ3k0eNWG94xV765gk4jn7YfC1WPIo95XRCztdNjvC7JIX41jd9QPF"

  private val TESTNET_API_KEY = "6WRxVZVP47ITHGX2QvBcKMkelIfvMZhCaBp37F2fIhqsvXmU8rw5cXr647x4p9Tj"
  private val TESTNET_SECRET_KEY = "Zzp5qNW4n82P1IAUviU0d6Bxg1W7Bxy4cQm1varNaUijxSjx6Y8AcNJUhqm5hzuV"


  def apply(apiKey: String, secretKey: String): BinanceSpotClientConfig =
    BinanceSpotClientConfig(apiKey, secretKey, DefaultUrls.TESTNET_URL)

  val testing = BinanceSpotClientConfig(TESTNET_API_KEY, TESTNET_SECRET_KEY)
}
