resolvers += "DefaultMavenRepository" at "http://repo1.maven.org/maven2/"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10.3"

sbt.Keys.fork := true
