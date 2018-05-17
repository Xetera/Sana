package listeners

import commands.CommandHandler
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter


object EventListener extends ListenerAdapter {
	override def onReady(event: ReadyEvent): Unit = {
		println("Scala Bot online and ready!")

		println(event.getJDA.asBot().getInviteUrl())
		CommandHandler.registerCommands()
		println(CommandHandler.commands.length)
		CommandHandler.commands.foreach(println)
	}

	override def onMessageReceived(event: MessageReceivedEvent): Unit = {
		val message = event.getMessage
		if (message.getAuthor.isBot)
			return
		println(s"${message.getAuthor.getName}: ${message.getContentDisplay}")
		val content = event.getMessage.getContentDisplay
		if (!Utils.isCommand(content))
			return

		val args: Option[(String, Array[String])] = Utils.getArgs(content)

		if (args.isEmpty){
			args.map(_ => print _ + " ")
			return
		}

		CommandHandler.executeCommand(args.get._1, message, args.get._2)

	}
}
