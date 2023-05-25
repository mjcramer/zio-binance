import sbt.{Test, ThisBuild}

ThisBuild / scalaVersion     := "3.2.2"
ThisBuild / organization     := "io.github.mjcramer"
ThisBuild / organizationName := "mjcramer"
ThisBuild / description      := "ZIO wrapper for Binance Java client"
ThisBuild / scmInfo          := Some(ScmInfo(url("https://github.com/mjcramer/zio-binance"), "https://github.com/mjcramer/zio-binance.git"))
ThisBuild / developers       := List(Developer("", "mjcramer", "mjcramer@gmail.com", url("https://github.com/mjcramer")))
ThisBuild / licenses         := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / homepage         := Some(url("https://github.com/mjcramer/zio-binance"))
ThisBuild / versionScheme    := Some("early-semver")
ThisBuild / git.useGitDescribe := true

enablePlugins(GitVersioning)
enablePlugins(GitBranchPrompt)

val common = Seq(
  scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "utf-8",
      "-explain",
      "-explaintypes",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
    )
  ,
  libraryDependencies ++= Seq(
    Modules.binanceConnector,
    Modules.enumeratum,
    Modules.enumeratumCirce,
  ) ++ Dependencies.zioBase
    ++ Dependencies.circe
    ++ Dependencies.logging
    ++ Dependencies.zioTesting.map(_ % Test),

  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
  Test / fork := true,

)

lazy val `zio-binance` = (project in file("."))
  .settings(
    crossScalaVersions := Nil,
    git.useGitDescribe := true,
    publish / skip := true
  )
  .aggregate(spot, examples)

lazy val spot = (project in file("spot"))
  .settings(common *)
  .settings(
    name := "zio-binance",
  )

//lazy val `websocket-api` = (project in file("websocket-api"))
//  .settings(common *)
//  .settings(
//    name := "zio-binance-wsapi",
//  )
//
//lazy val `websocket-streams` = (project in file("websocket-streams"))
//  .settings(common *)
//  .settings(
//    name := "zio-binance-wsstreams",
//  )

lazy val examples = (project in file("examples"))
  .settings(
    crossScalaVersions := Nil,
    publish / skip := true
  )
  .dependsOn(spot)

