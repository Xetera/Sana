package music

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result
import commands.CommandParameters
import net.dv8tion.jda.core.entities._
import utils.Utils


object MusicPlayer {

	var players: Map[String, GuildMusicManager] = Map[String, GuildMusicManager]()

	def getManager(guild: Guild): Option[GuildMusicManager] = {
		Some(players.getOrElse(guild.getId, null))
	}

	def connect(message: Message): Option[VoiceChannel] ={
		val guild: Guild = message.getGuild
		val targetChannel: VoiceChannel = message.getMember.getVoiceState.getChannel
		if (targetChannel == null){
			message.getChannel.sendMessage("❌ You are not in a voice channel").queue()
			return None
		}
		if (this.players.contains(guild.getId)){
			if (!targetChannel.getMembers.contains(message.getMember)){

			}
			return Some(targetChannel)
		}
		val manager = new GuildMusicManager(guild, targetChannel, message.getTextChannel)
		this.players += (guild.getId -> manager)
		Some(targetChannel)
	}

	def disconnect(message: Message): Boolean = {
		val guild: Guild = message.getGuild
		val targetChannel: VoiceChannel = message.getGuild.getSelfMember.getVoiceState.getChannel
		if (targetChannel == null){
			message.getChannel.sendMessage("❌ I am not in a voice channel").queue()
			return false
		}

		message.getGuild.getAudioManager.closeAudioConnection()
		message.getChannel.sendMessage("\uD83D\uDC4B").queue()
		true
	}

	def fetchItems(message: Message): Option[(VoiceChannel, GuildMusicManager)] = {
		val targetChannel: Option[VoiceChannel] = this.connect(message)
		if (targetChannel.isEmpty){
			return None
		}

		val manager: Option[GuildMusicManager] = this.getManager(message.getGuild)
		if (manager.isEmpty){
			return None
		}

		Some((targetChannel.get, manager.get))
	}

	def play(params: CommandParameters): Unit ={
		val message: Message = params.message
		val guild: Guild = params.message.getGuild

		val targetChannel: Option[VoiceChannel] = this.connect(message)
		val manager: Option[GuildMusicManager] = this.getManager(guild)

		if (targetChannel.isEmpty || manager.isEmpty){
			return
		}
		manager.get.play(params.message.getTextChannel, params.argString)
	}

	def setVolume(params: CommandParameters): Unit = {
		val message: Message = params.message
		val guild: Guild = params.message.getGuild

		val targetChannel: Option[VoiceChannel] = this.connect(message)
		val manager: Option[GuildMusicManager] = this.getManager(guild)

		if (targetChannel.isEmpty || manager.isEmpty){
			return
		}

		val number: Option[Int] = Utils.safeParseNumber(message, params.args(0))

		manager.get.setVolume(params.message.getTextChannel, number.get)
	}

	def getState(params: CommandParameters): Unit ={
		val message: Message = params.message
		val guild: Guild = params.message.getGuild

		val targetChannel: Option[VoiceChannel] = this.connect(message)
		val manager: Option[GuildMusicManager] = this.getManager(guild)

		if (targetChannel.isEmpty || manager.isEmpty){
			return
		}

		manager.get.getStatus(message.getTextChannel)
	}

	def resume(params: CommandParameters): Unit = {
		val message: Message = params.message
		val guild: Guild = params.message.getGuild

		val targetChannel: Option[VoiceChannel] = this.connect(message)
		val manager: Option[GuildMusicManager] = this.getManager(guild)

		if (targetChannel.isEmpty || manager.isEmpty){
			return
		}

		manager.get.resume(params.message.getTextChannel)
	}

	def pause(params: CommandParameters): Unit = {
		val message: Message = params.message
		val guild: Guild = params.message.getGuild

		val targetChannel: Option[VoiceChannel] = this.connect(message)
		val manager: Option[GuildMusicManager] = this.getManager(guild)

		if (targetChannel.isEmpty || manager.isEmpty){
			return
		}

		manager.get.pause(params.message.getTextChannel)
	}
}
