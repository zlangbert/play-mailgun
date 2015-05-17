name := "play-mailgun"

organization := "com.zlangbert"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.4.0-RC3" % "provided",
  "com.typesafe.play" %% "play-json" % "2.4.0-RC3" % "provided",
  "org.glassfish.jersey.core" % "jersey-client" % "2.17",
  "org.glassfish.jersey.media" % "jersey-media-multipart" % "2.17"
)

scalaVersion := "2.11.6"
scalacOptions ++= Seq("-deprecation", "-feature", "-Xlint", "-Xfatal-warnings")