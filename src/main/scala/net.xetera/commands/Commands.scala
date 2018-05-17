package commands
import java.io.File

import net.dv8tion.jda.core.entities.Icon

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
		example = "",
		usage = "",
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
		))
}