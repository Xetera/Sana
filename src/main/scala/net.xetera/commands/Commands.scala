package commands
import java.io.File

import listeners.CodeListener
import music.MusicPlayer
import net.dv8tion.jda.core.entities.Icon

import scala.util.Random

object Commands {
	val commands: Array[Command] = Array[Command](new Command(
		name = "ChangeName",
		description = "Renames Self",
		example = "~changename [Name]",
		usage = "~changename Scala Bot",
		run = (params: CommandParameters) => {
			val name = params.argString
			params.message.getJDA.getSelfUser.getManager.setName(name).queue(_ =>
				params.message.getChannel.sendMessage(s"Changed my name to $name").queue()
			)
		}),
	new Command(
		name = "Ping",
		description = "Gets ping",
		example = "~ping",
		usage = "~ping",
		run = (params: CommandParameters) => {
			val ping = params.message.getJDA.getPing
			params.message.getChannel.sendMessage(s"Pong! Your ping is: ${ping}ms").queue()
		}
	),
	new Command(
		name = "ChangeAvatar",
		description = "",
		example = "",
		usage = "",
		run = (params: CommandParameters) => {
			val attachment = params.message.getAttachments.get(0)
			val file: File = new File(attachment.getFileName)
			attachment.download(file)
			val icon = Icon.from(file)
			params.message.getJDA.getSelfUser.getManager.setAvatar(icon).queue(_ =>
				params.message.getChannel.sendMessage("Change my icon")
			)
		}
		),new Command(
			name = "echo",
			description = "",
			example = "",
			usage = "",
			run = (params: CommandParameters) => {
				params.message
					.getChannel
					.sendMessage(params.argString)
					.queue()
			}
		), new Command(
			name = "random",
			description= "Gets a random number",
			usage  = "~random",
			example= "~random [Random number]",
			run = (params: CommandParameters) => {
				val random: Random = new Random()
				val choice = params.args(0)

				val input: Either[Int, String] = try {
					Left(choice.toInt)
				} catch {
					case _: Exception => Right(s"**$choice** is not a valid input.")
				}

				val randomValue: String = input match {
					case Left(int) => int.toString
					case Right(str) => str
				}

				params.message.getChannel.sendMessage(randomValue).queue()
			}
		), new Command(
			name = "eval",
			description= "Evals",
			usage  = "",
			example= "",
			run = (params: CommandParameters) => {
				CodeListener.executeCode(params.message, params.argString)
			}
		), new Command(
			name = "play",
			description= "Plays a song",
			usage  = "",
			example= "",
			run = (params: CommandParameters) => {
				MusicPlayer.play(params)
			}
		), new Command(
			name = "volume",
			description= "Changes song volume",
			usage  = "",
			example= "",
			run = (params: CommandParameters) => {
				MusicPlayer.setVolume(params)
			}
		), new Command(
			name = "state",
			description= "Changes song volume",
			usage  = "",
			example= "",
			run = (params: CommandParameters) => {
				MusicPlayer.getState(params)
			}
		))
}