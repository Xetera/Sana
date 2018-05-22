package commands

import java.io.File

import listeners.CodeListener
import music.MusicPlayer
import net.dv8tion.jda.core.entities.Icon

import scala.util.Random

object Commands {
	val commands: Array[Command] = Array[Command](
		/*new Command(
		name = Left("ChangeName"),
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
			name = Left("changeavatar"),
			description = "Changes my avatar to something",
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
		),*/
		new Command(
			name = Left("ping"),
			description = "Shows my latency to the server",
			example = "~ping",
			usage = "~ping",
			run = (params: CommandParameters) => {
				val ping = params.message.getJDA.getPing
				params.message.getChannel.sendMessage(s"Pong! Your ping is: ${ping}ms").queue()

			}
		), new Command(
				name = Left("echo"),
				description = "Echoes input back to the channel",
				example = "",
				usage = "",
				run = (params: CommandParameters) => {
					params.message
						.getChannel
						.sendMessage(params.argString)
						.queue()
				}
			), new Command(
				name = Left("random"),
				description = "Gets a random number",
				usage = "~random",
				example = "~random [Random number]",
				run = (params: CommandParameters) => {
					val random: Random = new Random()
					val choice: String =
						if (params.args.isEmpty)
							"100"
						else
							params.args(0)

					val input: Either[Int, String] = try {
						Left(random.nextInt(choice.toInt))
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
				name = Left("eval"),
				description = "Evaluates scala code",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					CodeListener.executeCode(params.message, params.argString)
				}
			), new Command(
				name = Left("play"),
				description = "Plays a youtube link",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					MusicPlayer.play(params)
				}
			), new Command(
				name = Left("volume"),
				description = "Changes song volume",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					MusicPlayer.setVolume(params)
				}
			), new Command(
				name = Left("state"),
				description = "Shows my voice state?",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					MusicPlayer.getState(params)
				}
			), new Command(
				name = Left("resume"),
				description = "Resumes the player",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					MusicPlayer.resume(params)
				}
			), new Command(
				name = Left("pause"),
				description = "Pauses the player",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					MusicPlayer.pause(params)
				}
			), new Command(
				name = Right(Array[String]("np", "playing", "song")),
				description = "Shows the song that's currently playing",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					MusicPlayer.pause(params)
				}
			), new Command(
				name = Right(Array[String]("connect", "join")),
				description = "Connects to a channel",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					MusicPlayer.connect(params.message)
				}
			), new Command(
				name = Right(Array[String]("disconnect", "leave")),
				description = "Disconnects from a channel",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					MusicPlayer.disconnect(params.message)
				}
			), new Command(
				name = Left("help"),
				description = "Gets help",
				usage = "",
				example = "",
				run = (params: CommandParameters) => {
					var help: String = "```cs\n#Help\n#Everything is experimental\n"
					for (command <- CommandHandler.commands) {
						val commandName =
							if (command.name.isLeft)
								command.name.left.get
							else
								command.name.right.get(0)
						help = help.concat("^" + commandName + ": \"" + command.description + "\"\n")
					}
					help = help.concat("```")
					params.message.getChannel.sendMessage(help.toString).queue()
				}
			))
}