import sbt._

object Dependencies {
  import Modules._

  lazy val circe   = Seq(circeCore, circeParser, circeGeneric)
  lazy val logging = Seq(slf4j, logback, scalaLogging)
  lazy val testing = Seq(scalamock, scalatest, scalatestJunit)

  lazy val zioBase    = Seq(zio, zioLogging)
  lazy val zioTesting = Seq(zioTest, zioTestSbt)
}

object Versions {
  lazy val binanceConnector        = "2.0.0rc2"
  lazy val circe                   = "0.14.5"
  lazy val enumeratum              = "1.7.2"
  lazy val enumeratumCirce         = "1.7.2"
  lazy val logback                 = "1.4.6"
  lazy val scalaLogging            = "3.9.5"
  lazy val scalacheck              = "1.17.0"
  lazy val scalamock               = "5.2.0"
  lazy val scalatest               = "3.2.15"
  lazy val scalatestPlusScalacheck = "3.2.2.0"
  lazy val scalatestJunit          = "3.2.15.0"
  lazy val slf4j                   = "2.0.7"
  lazy val zio                     = "2.0.10"
  lazy val zioLogging              = "2.1.11"
}

object Modules {
  lazy val binanceConnector      = "io.github.binance" % "binance-connector-java" % Versions.binanceConnector
  lazy val circeCore             = "io.circe"         %% "circe-core"             % Versions.circe
  lazy val circeGeneric          = "io.circe"         %% "circe-generic"          % Versions.circe
  lazy val circeGenericExtras    = "io.circe"         %% "circe-generic-extras"   % Versions.circe
  lazy val circeParser           = "io.circe"         %% "circe-parser"           % Versions.circe
  lazy val circeShapes           = "io.circe"         %% "circe-shapes"           % Versions.circe
  lazy val circeFs2              = "io.circe"         %% "circe-fs2"              % Versions.circe

  lazy val enumeratum     = "com.beachape"               %% "enumeratum"      % Versions.enumeratum
  lazy val enumeratumCirce     = "com.beachape"               %% "enumeratum-circe"      % Versions.enumeratumCirce
  lazy val slf4j          = "org.slf4j"                   % "slf4j-api"       % Versions.slf4j
  lazy val logback        = "ch.qos.logback"              % "logback-classic" % Versions.logback
  lazy val scalaLogging   = "com.typesafe.scala-logging" %% "scala-logging"   % Versions.scalaLogging
  lazy val scalatest      = "org.scalatest"              %% "scalatest"       % Versions.scalatest
  lazy val scalacheck     = "org.scalacheck"             %% "scalacheck"      % Versions.scalacheck
  lazy val scalamock      = "org.scalamock"              %% "scalamock"       % Versions.scalamock
  lazy val scalatestJunit = "org.scalatestplus"          %% "junit-4-13"      % Versions.scalatestJunit

  lazy val zioLogging = "dev.zio" %% "zio-logging-slf4j" % Versions.zioLogging

  lazy val zio             = "dev.zio" %% "zio"               % Versions.zio
  lazy val zioStreams      = "dev.zio" %% "zio-streams"       % Versions.zio
  lazy val zioTest         = "dev.zio" %% "zio-test"          % Versions.zio
  lazy val zioTestSbt      = "dev.zio" %% "zio-test-sbt"      % Versions.zio
  lazy val zioTestMagnolia = "dev.zio" %% "zio-test-magnolia" % Versions.zio

}
