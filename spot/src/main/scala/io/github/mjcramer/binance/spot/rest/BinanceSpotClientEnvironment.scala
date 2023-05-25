package io.github.mjcramer.binance.spot.rest

import com.binance.connector.client.SpotClient

private[rest] trait BinanceSpotClientEnvironment {
  protected def client: SpotClient
}
