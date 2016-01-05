enablePlugins(JavaServerAppPackaging)

name := "Makler"

version := "0.1"

scalaVersion := "2.11.7"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  "Spray Repository"    at "http://repo.spray.io")

EclipseKeys.withSource := true

libraryDependencies ++= {
  val akkaVersion       = "2.4.1"
  val sprayVersion      = "1.3.2"
  Seq(
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
    "io.spray"          %% "spray-can"       % sprayVersion,
    "io.spray"          %% "spray-routing"   % sprayVersion,
    "io.spray"          %% "spray-json"      % sprayVersion,
    "io.spray"          %% "spray-caching"   % sprayVersion,
    "io.spray"          %% "spray-client"   % sprayVersion,
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.1.2",
    "com.typesafe.akka" %% "akka-testkit"    % akkaVersion  % "test",
    "io.spray"          %% "spray-testkit"   % sprayVersion % "test",
    "org.specs2"        %% "specs2"          % "2.3.13"     % "test"
  )
}

// Assembly settings
mainClass in Global := Some("pl.com.agora.makler.app.Main")

jarName in assembly := "makler.jar"
