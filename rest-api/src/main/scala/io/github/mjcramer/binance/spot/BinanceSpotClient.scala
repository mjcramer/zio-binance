package io.github.mjcramer.binance.spot

import com.binance.connector.client.SpotClient
import com.binance.connector.client.enums.DefaultUrls
import com.binance.connector.client.impl.SpotClientImpl
import zio.*

import java.io.Closeable

case class BinanceSpotClient(wrapped: SpotClient)

object BinanceSpotClient {
  lazy val layer: URLayer[BinanceSpotClientConfig, BinanceSpotClient] =
    ZLayer {
      for {
        config <- ZIO.service[BinanceSpotClientConfig]
        client = SpotClientImpl(config.apiKey, config.secretKey)
      } yield BinanceSpotClient(client)
    }

  lazy val testLayer: URLayer[BinanceSpotClientConfig, BinanceSpotClient] =
    ZLayer {
      for {
        config <- ZIO.service[BinanceSpotClientConfig]
        client = SpotClientImpl(config.apiKey, config.secretKey, DefaultUrls.TESTNET_URL)
      } yield BinanceSpotClient(client)
    }
}
