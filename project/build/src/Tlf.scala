import sbt._

class TLFProject(info: ProjectInfo) extends DefaultProject(info)
{
  val scalatest = "org.scala-tools.testing" % "scalatest" % "0.9.5"
}


