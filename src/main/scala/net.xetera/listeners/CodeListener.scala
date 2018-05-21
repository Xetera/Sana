package listeners

import scala.util.matching.Regex
import com.twitter.util.Eval
//import handlers.requests.RequestMaker
import net.dv8tion.jda.core.entities.Message

import scala.util.parsing.json.JSONObject

object CodeListener {
	val languages: Map[String, Int] = Map[String, Int](
		"bash" -> 6,
		"coffeescript" -> 21,
		"cpp" -> 12,
		"cs" -> 11,
		"csharp" -> 11,
		"py" -> 63,
		"python" -> 63,
		"scala" -> 71,
		"R" -> 67,
		"javascript" -> 49,
		"js" -> 49,
		"java" -> 40
	)

	def checkMessage(message: Message): Unit = {
		println("Checking message")
		// @([^\n+]) matching anything
		val test = new Regex("""```([^\n]+)((.|\n)*)```""", "gm")
		message.getContentRaw match {
			case test(language, code, _*)  =>
//				val id: Int = this.languages.getOrElse(language, default = return)
//				val data: Map[String, Any] = Map[String, Any](
//					"id" -> id,
//					"sourceCode" -> Utils.trimEnds(code).toString
//				)
//				println(s"$data")
//				RequestMaker.post("http://cloudcompiler.esy.es/api/submissions", data)
//				println(language)
//				println(code)
				this.executeCode(message, code)
			case _ => None
		}
	}

	def executeCode(message: Message, code: String): Unit ={
		val eval = new Eval()

		try {
			val result = eval(code).toString
			val channel = message.getChannel
			if (result != "()"){
				channel sendMessage result queue()
			}
			message.addReaction("✅")
				.queue()
		} catch {
			case exception : Exception =>
				println(s"Error executing code\n$exception")
				message addReaction "❌" queue()
		}
	}
}
