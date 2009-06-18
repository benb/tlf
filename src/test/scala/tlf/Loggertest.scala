package tlf
import tlf.Level._
import org.scalatest.Suite


class LoggingTest extends Suite{
  def testLevel{
    Logging.toStdout
    var count =0
    class Foo extends Logging{
      def test(level:Level){log(level,{count+=1; count.toString})}
    }
    val foo = new Foo
    foo.logLevel=Debug
    foo.test(Info)
    assert(count==1)

    //shouldn't evaluate as not logged at this level
    foo.test(Finest)
    assert(count==1)


    //only evaluate string once even for >1 print stream
    Logging.addPrintStream(System.out)
    foo.test(Info)
    assert(count==2)


    //shouldn't log...
    val foono= new Foo
    foono.test(Debug)
    assert(count==2)

    //... but this should - as it should inherit new default
    Logging.setLevel("Debug")
    val foo2 = new Foo
    foo2.test(Debug)
    assert(count==3)
  }

  def testStopwatch{
    Logging.reset
    Logging.setLevel("Debug")
    Logging.toStdout
    val watch = new Stopwatch
    watch.start
    watch.reset
    Thread.sleep(2000)
    assert(watch.reset > 1999)
  }

  def testTime{
    val millis = (1000L*60*60*2 + 1000L*60*32 + 23132L)
    val t = new Time(millis)
    println(millis)
    println(t)
    assert(t.toString == "02:32:23.132")
    val millis2 = (1000L*60*60*312+ 1000L*60*3 + 47236L)
    val t2 = new Time(millis2)
    assert(t2.toString == "312:03:47.236")
  }
}
