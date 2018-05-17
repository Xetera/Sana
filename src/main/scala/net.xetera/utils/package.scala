import settings.BotSettings

package object Utils {
	def isCommand(content: String): Boolean = {
		content.startsWith(BotSettings.prefix.toString)
	}

	def getArgs(content: String): Option[(String, Array[String])] = content.charAt(0) match {
		case '~' =>
			val cleanContent: String = content.substring(1)
			val words = cleanContent.split(" ")
			val tuple = Tuple2(words(0), words.slice(1, words.length))
			Some(tuple)
		case _ => None
	}
}
