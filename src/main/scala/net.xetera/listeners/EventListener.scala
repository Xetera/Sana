package listeners

import commands.CommandHandler
import net.dv8tion.jda.core.events.channel.voice.update.GenericVoiceChannelUpdateEvent
import net.dv8tion.jda.core.events.guild.voice.GenericGuildVoiceEvent
import net.dv8tion.jda.core.events.{ExceptionEvent, ReadyEvent}
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import utils.Utils


object EventListener extends ListenerAdapter {
	override def onReady(event: ReadyEvent): Unit = {
		println("Scala Bot online and ready!")

		println(event.getJDA.asBot().getInviteUrl())
		CommandHandler.registerCommands()
	}

	override def onMessageReceived(event: MessageReceivedEvent): Unit = {
		val message = event.getMessage

		if (message.getAuthor.isBot)
			return

		println(s"${message.getAuthor.getName}: ${message.getContentDisplay}")
		val content = event.getMessage.getContentDisplay
		if (!Utils.isCommand(content)) {
			CodeListener.checkMessage(message)
			return
		}

		val args: Option[(String, Array[String])] = Utils.getArgs(content)

		if (args.isEmpty){
			args.map(_ => print _ + " ")
			return
		}

		CommandHandler.executeCommand(args.get._1, message, args.get._2)

	}

	override def onException(event: ExceptionEvent): Unit = {
		println("GOT AN EXCEPTION")
		println(event.toString)
	}

	override def onGenericGuildVoice(event: GenericGuildVoiceEvent): Unit = {
		println("VOICE CHANNEL UPDATE")
		if (!event.getMember.getVoiceState.inVoiceChannel){
			return
		}

		event.getVoiceState.getChannel.getMembers.toArray().foreach(println)
	}
}
