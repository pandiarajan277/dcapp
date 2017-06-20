name := """dcapp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
 
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

//libraryDependencies +="mysql" % "mysql-connector-java" % "5.1.34"

libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.1.1"

libraryDependencies += "com.typesafe.play" %% "play-slick" % "2.0.0"

libraryDependencies +="com.typesafe.play" %% "play-slick-evolutions" % "2.0.0"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1200-jdbc41"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
