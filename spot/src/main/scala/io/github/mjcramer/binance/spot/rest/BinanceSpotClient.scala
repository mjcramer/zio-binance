package io.github.mjcramer.binance.spot.rest

import com.binance.connector.client.SpotClient
import com.binance.connector.client.enums.DefaultUrls
import com.binance.connector.client.impl.SpotClientImpl
import com.typesafe.scalalogging.StrictLogging
import zio.*

import java.io.Closeable

case class BinanceSpotClient(wrapped: SpotClient)

object BinanceSpotClient extends StrictLogging {
  lazy val layer: URLayer[BinanceSpotClientConfig, BinanceSpotClient] =
    ZLayer {
      for {
        config <- ZIO.service[BinanceSpotClientConfig]
      } yield BinanceSpotClient(SpotClientImpl(config.apiKey, config.secretKey))
    }

  lazy val testLayer: URLayer[BinanceSpotClientConfig, BinanceSpotClient] =
    ZLayer {
      for {
        config <- ZIO.service[BinanceSpotClientConfig]
        _ <- ZIO.succeed(logger.debug(s"Using testnet url ${DefaultUrls.TESTNET_URL}"))
      } yield BinanceSpotClient(SpotClientImpl(config.apiKey, config.secretKey, DefaultUrls.TESTNET_URL))
    }
}
