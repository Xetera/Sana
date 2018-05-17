package commands

import scala.util.Random

object RandomNumberCommand extends Command(
	name = "random",
	description= "Gets a random number",
	usage  = "~random ",
	example= "~random [Random number]",
	run = (params: CommandParameters) => {
		val random: Random = new Random()
		val choice = params.args(0)

		val input: Int = try {
			choice.toInt
		} catch {
			case _: Exception => 100
		}
		val randomValue = random.nextInt(input)
		params.message.getChannel.sendMessage(randomValue.toString)
	}
) {}
