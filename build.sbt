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

lazy val compilerOptions = Seq(
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

lazy val `zio-binance` = (project in file("."))
  .settings(
    crossScalaVersions := Nil,
    git.useGitDescribe := true,
    publish / skip := true
  )
  .aggregate(`rest-api`, `websocket-api`, `websocket-streams`, examples)


lazy val `rest-api` = (project in file("rest-api"))
  .settings(
    name := "zio-binance-rest",
    scalacOptions ++= compilerOptions,
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

lazy val examples = (project in file("examples"))
  .settings(
    crossScalaVersions := Nil,
    publish / skip := true
  )
  .dependsOn(`rest-api`)

lazy val `websocket-api` = (project in file("websocket-api"))
lazy val `websocket-streams` = (project in file("websocket-streams"))



///*
// * The settings file is used to specify which projects to include in your build.
// *
// * Detailed information about configuring a multi-project build in Gradle can be found
// * in the user manual at https://docs.gradle.org/8.0.2/userguide/multi_project_builds.html
// */
//
//// The following block must appear before any other statement
//pluginManagement {
//  // Include 'plugins build' to define convention plugins.
//  includeBuild('build-logic')
//  repositories {
//    gradlePluginPortal()
//  }
//}
//
//rootProject.name = 'seer-binance'
//include('common', 'rest') //, 'ticker')
//
//
//def scalaBinVersion = '3' // scalaVersion?.replaceAll(/(\d+)\.(\d+)\..*/, '$1.$2')?.trim()
//def scalify = { text ->
//  "${text}_${scalaBinVersion}"
//}
//
//
//dependencyResolutionManagement {
//  versionCatalogs {
//    libs {
//      //            library('groovy-core', 'org.codehaus.groovy', 'groovy').versionRef('groovy')
//      //            library('groovy-json', 'org.codehaus.groovy', 'groovy-json').versionRef('groovy')
//      //            library('groovy-nio', 'org.codehaus.groovy', 'groovy-nio').versionRef('groovy')
//      //            library('commons-lang3', 'org.apache.commons', 'commons-lang3').version {
//      //                strictly '[3.8, 4.0['
//      //                prefer '3.9'
//      //            }
//      //            bundle('groovy', ['groovy-core', 'groovy-json', 'groovy-nio'])
//    }
//  }
//}

//import sbt.Global
//// -----------------------------------------------------------------------------------------------------------------
////   project name and description
//// -----------------------------------------------------------------------------------------------------------------
//name := "sbt-genetica"
//Global / organization := "co.vitalfish"
//Global / onChangedBuildSource := ReloadOnSourceChanges
//
//// -----------------------------------------------------------------------------------------------------------------
////   publish configuration
//// -----------------------------------------------------------------------------------------------------------------
//resolvers += "Google Artifact Registry" at "artifactregistry://us-west1-maven.pkg.dev/vital-fish/genetica-artifacts"
//ThisBuild / versionScheme := Some("early-semver")
//
///* ********************************************************************************************************************
//     SBT-GENETICA - SBT Plugin to standardize dependencies and configuration for genetica microservices
//   ********************************************************************************************************************/
//lazy val root = (project in file("."))
//  .enablePlugins(SbtPlugin)
//  .enablePlugins(GitVersioning)
//  .enablePlugins(GitBranchPrompt)
//  .settings(
//    sbtPlugin := true,
//    git.gitTagToVersionNumber := { tag: String =>
//      if(tag matches "[0-9]+\\..*") Some(tag)
//      else None
//    },
//    git.useGitDescribe := true,
//
//    /**
//      * These are the plugins that get added to the project that uses this plugin
//      */
//    // Enables the use of protobuf and service/message protocols with gprc
//    addSbtPlugin("com.lightbend.akka.grpc" % "sbt-akka-grpc" % "1.1.1"),
//    // Enables the use of Lightbend Telemetry
//    addSbtPlugin("com.lightbend.cinnamon" % "sbt-cinnamon" % "2.15.0"),
//    // Docker container packaging
//    addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.8.0"),
//    // Enables use of git for versioning
//    addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0"),
//    // Adds build information to compiled code
//    addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.10.0"),
//    // Adds support for google artifact registry
//    addSbtPlugin("org.latestbit" % "sbt-gcs-plugin" % "1.5.0"),
//    // Enable coverage statistics and reports
//    addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.1"),
//    // Scala formatting
//    addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.2"),
//
//    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % Test,
//
//    publishTo := Some("Google Artifact Registry" at "artifactregistry://us-west1-maven.pkg.dev/vital-fish/genetica-artifacts"),
//    publishMavenStyle := true
//  )
//
//console / initialCommands := "import co.vitalfish.genetica._"
