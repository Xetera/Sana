package commands

import net.dv8tion.jda.core.entities.{Icon, Message}
import utils.Utils

import scala.collection.mutable.ArrayBuffer


trait CommandParameters {
	val message: Message
	val args: Array[String]
	val argString: String
}

class Command(
	var name: Either[String, Array[String]],
	var description: String,
	var usage: String,
	var example: String,
	var run: (CommandParameters) => Unit
	) {

	CommandHandler.registerCommand(this)
	override def toString: String =
		if (this.name.isLeft)
			this.name.left.get
		else
			this.name.right.get(0)

}

object CommandHandler {
	val commands: ArrayBuffer[Command] = new ArrayBuffer[Command]()

	def registerCommand: Command => Unit = {
		this.commands += _
	}

	def executeCommand(targetName: String, message: Message, args: Array[String]): Unit = {
		if (this.commands.isEmpty){
			println("No commands were found")
			return
		}

		val matches: Option[Command] = this.commands.find(
			command => if (command.name.isLeft){
				Utils.compareCaseInsensitive(command.name.left.get, targetName)
			} else {
				Utils.compareCaseInsensitive(command.name.right.get.find(
					subCommands => Utils.compareCaseInsensitive(subCommands, targetName)
				).getOrElse(""), targetName)
			}
		)
		val values = (message, args)
		val parameter = new CommandParameters {
			override val message: Message = values._1
			override val args: Array[String] = values._2
			override val argString: String = args.mkString(" ")
		}

		val command: Command = matches.getOrElse {
			println(s"Could not find the command ($targetName)")
			return
		}

		command.run(parameter)
	}

	def registerCommands(): Unit ={
		Commands.commands.apply(0)
	}
}
