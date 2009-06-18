package tlf
import java.util.logging._
import java.util.logging.Level._

/**
Extend this in your classes to include logging facilities
*/
trait Logging {
  import Level._
  /**
    The log level used by this object
  */
  var logLevel=Logging.level 
  def log(level:Level,s: => String){if (level >= logLevel){Logging.unconditionalLog(s)}}
  def debug(s: => String)=log(Debug,s)
  def extra(s: => String)=log(Extra,s)
  def finest(s: => String)=log(Finest,s)
  def info(s: => String)=log(Info,s)
  def warning(s: => String)=log(Warning,s)
  def severe(s: => String)=log(Severe,s)
  
 }


/**
 Possible logging levels from Severe (most important messages) to Finest
*/
object Level extends Enumeration("Finest","Debug","Extra","Info","Warning","Severe"){
  type Level=Value
  val Finest,Debug,Extra,Info,Warning,Severe=Value
}

/**
This object controls where logging messages go to and provides utility methods
*/
object Logging{
  import java.io._
  import Level._

  private var output:List[PrintStream]=List()
  /**
   Default level for newly created Logging objects
  */
  var level = Severe

  def reset{
    level=Severe
    output=List()
  }
  
  /**
   Add Stdout as a destination for log messages
  */
  def toStdout{
    if (!(output.exists(_==System.out))){
      addPrintStream(System.out)
    }
  }

  /**
   Add Stderr as a destination for log messages
  */
  def toStderr{
    if (!(output.exists(_==System.err))){
      addPrintStream(System.err)
    }
  }


  /**
   Add a printstream to log to
  */
  def addPrintStream(p:PrintStream){
    output=p::output
  }

  /**
   log a message (contitional on the log level)
  */
  def log(l:Level,s: => String){
    if (level<=l){
      unconditionalLog(s)
    }
  }
  private def unconditionalLog(s: => String){
    lazy val s2:String=s // Only compute once if streams are present, 0 if not
    output.foreach{p=>p.println(s2)}
  }

  /**
   Set the default log level with a string (e.g. "Debug" or "Severe").
  */
  def setLevel(l:String){
    level =  valueOf(l(0).toUpperCase+l.drop(1).toLowerCase).getOrElse(level)
  }


}
