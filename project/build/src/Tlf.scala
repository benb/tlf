import sbt._

class TLFProject(info: ProjectInfo) extends DefaultProject(info)
{
  val newReleaseToolsRepository = "Scala Tools Repository" at " http://nexus.scala-tools.org/content/repositories/snapshots/" 
  val scalatest = "org.scalatest" % "scalatest" % "1.2-for-scala-2.8.0.RC6-SNAPSHOT" 
  override def managedStyle = ManagedStyle.Maven 
  lazy val publishTo = Resolver.file("Publish", new java.io.File("../benb.github.com/maven/")) 
}


