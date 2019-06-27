ThisBuild / scalaVersion := "2.12.3"
ThisBuild / organization := "alex"

lazy val phenix = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "Phenix",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,

  );
