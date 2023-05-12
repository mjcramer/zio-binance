package io.github.mjcramer.binance.websocket

import zio.*
import java.io.Closeable

case class BinanceWebsocketStreamClient(wrapped: WebsocketStreamClient)


object BinanceWebsocketStreamClient {
  lazy val layer: URLayer[BinanceSpotClientConfig, BinanceWebsocketStreamClient] =
    ZLayer {
      for {
        config <- ZIO.service[BinanceSpotClientConfig]
        client = WebSocketStreamClientImpl(config.apiKey, config.secretKey)
      } yield BinanceSpotClient(client)
    }

  lazy val testLayer: URLayer[BinanceSpotClientConfig, BinanceWebsocketStreamClient] =
    ZLayer {
      for {
        config <- ZIO.service[BinanceSpotClientConfig]
        client = WebSocketStreamClientImpl(config.apiKey, config.secretKey, DefaultUrls.TESTNET_URL)
      } yield BinanceSpotClient(client)
    }
}
