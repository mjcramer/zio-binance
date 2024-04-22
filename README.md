
# zio-binance
ZIO wrapper for the Binance Java client. 

This is a work in progress...

## Usage

Set the environment variables for the Binance API and secret keys. In my example, I use the 1password CLI to retrieve my password 

```
set -x BINANCE_SECRET_KEY (op item get "Binance" --fields "Spot Test API Keys.Secret Key")
set -x BINANCE_API_KEY (op item get "Binance" --fields "Spot Test API Keys.API Key")
```

Run examples 
```shell
sbt "runMain io.github.mjcramer.binance.examples.ZioBinanceStreamExample"
```