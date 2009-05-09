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
}
