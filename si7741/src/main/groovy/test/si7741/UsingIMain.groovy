package test.si7741

import scala.Function1
import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.NamedParamClass

class UsingIMain {


	static main(args){


		def ignoreinnercls = false
		if(args && args.length == 1){
			ignoreinnercls = Boolean.parseBoolean(args[0])
		}

		def settings = new Settings()

		def imain = new IMain(settings)
		imain.setContextClassLoader()

		imain.interpret("""
                   import scala.tools.nsc.interpreter._
				   import scala.tools.nsc._
                   def getIMain(cls:ClassLoader) = {
					   val s = new Settings
                       //s.ignoreinnercls.value = $ignoreinnercls
                       s.usejavacp.value = true
                       s.embeddedDefaults(cls)
                       new IMain(s){
                         override protected def parentClassLoader:ClassLoader = cls
                       }
                   }
				   val m = getIMain _
                """)


		def myModule = new MyModuleImpl()

		def v = imain.valueOfTerm("m").get().asType(Function1).apply(
				myModule.getClass().getClassLoader())
		v.setContextClassLoader()

		def ctx1 = ["module": myModule]
		imain.bind(new NamedParamClass("ctx", "java.util.Map[String, Object]", ctx1))
		v.bind(new NamedParamClass("ctx", "java.util.Map[String, Object]", ctx1))

		v.interpret("""
		    import scala.collection.JavaConversions._
		    import test.si7741._
                    println("CTX " + ctx)
                    val mymodule = ctx("module").asInstanceOf[MyModuleImpl]
                    println("!!!!!! Result: " + mymodule.mymethod())

		""")
	}
}
