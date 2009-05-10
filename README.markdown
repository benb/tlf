Trival Logging Framework for Scala
==================================

A trivially small library for use in my own projects, that implements a trait,
Logging, that can be inherited by your classes.  A class can then call various
logging levels with either a String, or a function that returns a String. If
the log level is not activated then the function will not be evaluated, so
Strings are not created unnecessarily.

To build, you need the [simple-build-tool](http://code.google.com/p/simple-build-tool/)

To compile:
    sbt compile

To make a jar file:
    sbt package

To run the tests:
    sbt update # run once to download Scalatest
    sbt test

The code is licensed under the MIT license (aka the X11 license).



Example usage:


    import tlf.Level._
    import tlf.Logging
    
    object Test{
      def main(args:Array[String]){
    
        //basic usage:
        val foo = new Foo
        foo.testLog
        assert(foo.timesLogged==0)
        Logging.toStdout // add STDOUT output stream
        foo.testLog
        assert(foo.timesLogged==0) // Still won't log as default threshold is Severe only
        foo.logLevel=Info
        foo.testLog
        assert(foo.timesLogged==1) // This will now produce a log message
    
        //mixing in to unlogged class to log a method
        val bar = new Bar with Logging{override def exampleMethod={val x = super.exampleMethod; debug("Example Method " + x); x}}
        bar.logLevel=Debug
        val ans = bar.exampleMethod
        assert(ans==1)
    
      }
    }
    
    class Foo extends Logging{
       var timesLogged=0
       def testLog{
         info{
           //this only executed when actually logged
           timesLogged+=1 
           "Log message"
         }
       }
    }
    class Bar{
      def exampleMethod=1
    }
