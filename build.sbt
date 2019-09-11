ThisBuild / scalaVersion := "2.12.9"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "fp-fundamentals",
    scalacOptions ++= Seq(
      "-Ypartial-unification",
      "-language:higherKinds"
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.10"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.7"
    )
  )