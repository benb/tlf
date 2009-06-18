package tlf
import Logging._
import Level._
import java.text.DecimalFormat
object Form extends DecimalFormat("00"){
  def apply(d:Double)=format(d)
}
class Time(milliseconds:Long){
  lazy val hours = milliseconds/(1000*60*60)
  lazy val minutes = (milliseconds%(1000*60*60))/(1000*60)
  lazy val seconds = BigDecimal(milliseconds%(1000*60))/1000
  override def toString = Form(hours) +":" + Form(minutes) + ":" + seconds
}

class Stopwatch extends Logging{
  var level=Debug
  var time=System.currentTimeMillis
  def start{time=System.currentTimeMillis}
  def logTime=log(level,{new Time(System.currentTimeMillis-time).toString})
  def reset = {
        val oldT = time
        time = System.currentTimeMillis
        log(level,{new Time(time-oldT).toString})
        time-oldT
      }
}
