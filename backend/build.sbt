name := "backend"
 
version := "1.0" 
      
lazy val `backend` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.13.3"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.2.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.0"



libraryDependencies ++= Seq(
    //json4s native:
    "org.json4s" %% "json4s-native" % "3.6.9",
    "org.json4s" %% "json4s-jackson" % "3.6.9",
    "org.json4s" %% "json4s-ext" % "3.6.9",
)


libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies += "org.scalatest" % "scalatest_2.13" % "3.2.3" % "test"


unmanagedResourceDirectories in Test +=  (baseDirectory.value / "target/web/public/test")

