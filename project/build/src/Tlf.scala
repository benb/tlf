import sbt._

class TLFProject(info: ProjectInfo) extends DefaultProject(info)
{
  val scalatest = "org.scala-tools.testing" % "scalatest" % "0.9.5"
  override def managedStyle = ManagedStyle.Maven 
  lazy val publishTo = Resolver.file("Publish", new java.io.File("../benb.github.com/maven/ ")) 
}


