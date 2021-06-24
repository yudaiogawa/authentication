name := """play-authentication"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  guice,
  ehcache,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)
