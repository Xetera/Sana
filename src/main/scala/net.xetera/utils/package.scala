import net.dv8tion.jda.core.entities.Message
import settings.BotSettings

package object Utils {
	def isCommand(content: String): Boolean = {
		content.startsWith(BotSettings.prefix.toString)
	}

	def getArgs(content: String): Option[(String, Array[String])] = content.charAt(0) match {
		case BotSettings.prefix =>
			val cleanContent: String = content.substring(1)
			val words = cleanContent.split(" ")
			val tuple = Tuple2(words(0), words.slice(1, words.length))
			Some(tuple)
		case _ => None
	}

	def trimEnds(input: String): String = {
		input.split('\n').map(_.trim.filter(_ >= ' ')).mkString
	}

	def safeParseNumber(message: Message, input: String): Option[Int] = {
		val number: Option[Int] = try {
			Some(input.toInt)
		}
		catch {
			case _: NumberFormatException =>
				message.getChannel.sendMessage(s"**$input** must be a number.")
				return None
		}
		number
	}
}
