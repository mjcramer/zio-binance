package io.github.mjcramer.binance.spot

import com.binance.connector.client.SpotClient

private[spot] trait BinanceSpotClientEnvironment {
  protected def client: SpotClient
}
