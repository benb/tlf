package tlf
import java.util.logging._
import java.util.logging.Level._

trait Logging {
  import Level._
  var logLevel=Logging.level //default
  def log(level:Level,s: => String){if (level >= logLevel){Logging.unconditionalLog(s)}}
  def debug(s: => String)=log(Debug,s)
  def extra(s: => String)=log(Extra,s)
  def finest(s: => String)=log(Finest,s)
  def info(s: => String)=log(Info,s)
  def warning(s: => String)=log(Warning,s)
  def severe(s: => String)=log(Severe,s)
  
 }


object Level extends Enumeration("Finest","Debug","Extra","Info","Warning","Severe"){
  type Level=Value
  val Finest,Debug,Extra,Info,Warning,Severe=Value
}
object Logging{
  import java.io._
  import Level._

  private var output:List[PrintStream]=List()
  var level = Severe
  
  def toStdout{
    if (!(output.exists(_==System.out))){
      addPrintStream(System.out)
    }
  }

  def enableDebug={
    if(!(output contains (System.out))){
      addPrintStream(System.out)
    }
    level=Debug
  }
  def addPrintStream(p:PrintStream){
    output=p::output
  }
  def log(l:Level,s: => String){
    if (level<=l){
      unconditionalLog(s)
    }
  }
  private def unconditionalLog(s: => String){
    lazy val s2:String=s // Only compute once if streams are present, 0 if not
    output.foreach{p=>p.println(s2)}
  }
  def setLevel(l:String){
    level =  valueOf(l(0).toUpperCase+l.drop(1).toLowerCase).getOrElse(level)
  }
}
