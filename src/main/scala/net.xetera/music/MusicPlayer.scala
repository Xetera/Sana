package music

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result
import commands.CommandParameters
import net.dv8tion.jda.core.entities._


object MusicPlayer {
//	def getAllInVoice(guild: Guild): Map[VoiceChannel, Array[Member]] = {
//		val voiceChannels: Array[VoiceChannel] = guild.getVoiceChannels.toArray().asInstanceOf[Array[VoiceChannel]]
//
//		voiceChannels.foldLeft(Map[VoiceChannel, Array[Member]]())((item, v) =>{
//			item(v.getId) = v.getMembers.toArray()
//		})
//	}
	var players: Map[String, GuildMusicManager] = Map[String, GuildMusicManager]()

	def getManager(guild: Guild): Option[GuildMusicManager] = {
		Some(players.getOrElse(guild.getId, null))
	}

	def connect(message: Message): Option[VoiceChannel] ={
		val guild: Guild = message.getGuild
		val targetChannel: VoiceChannel = message.getMember.getVoiceState.getChannel
		if (targetChannel == null){
			message.getChannel.sendMessage("❌ > You are not in a voice channel")
			return None
		}
		val manager = new GuildMusicManager(guild, targetChannel, message.getTextChannel)
		this.players += (guild.getId -> manager)
		Some(targetChannel)
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
		if (targetChannel.isEmpty){
			return
		}

		val manager: Option[GuildMusicManager] = this.getManager(guild)
		if (manager.isEmpty){
			return
		}
		manager.get.play(params.message.getTextChannel, params.argString)
	}

	def setVolume(params: CommandParameters): Unit = {
		val message: Message = params.message
		val guild: Guild = params.message.getGuild

		val targetChannel: Option[VoiceChannel] = this.connect(message)
		if (targetChannel.isEmpty){
			return
		}

		val manager: Option[GuildMusicManager] = this.getManager(guild)
		if (manager.isEmpty){
			return
		}

		val number: Option[Int] = Utils.safeParseNumber(message, params.args(0))

		manager.get.setVolume(params.message.getTextChannel, number.get)
	}

	def getState(params: CommandParameters): Unit ={
		val message: Message = params.message
		val guild: Guild = params.message.getGuild

		val targetChannel: Option[VoiceChannel] = this.connect(message)
		if (targetChannel.isEmpty){
			return
		}

		val manager: Option[GuildMusicManager] = this.getManager(guild)
		if (manager.isEmpty){
			return
		}

		manager.get.getStatus(message.getTextChannel)
	}

	def pause(params: CommandParameters): Unit = {
		val message: Message = params.message
		val guild: Guild = params.message.getGuild

		val targetChannel: Option[VoiceChannel] = this.connect(message)
		if (targetChannel.isEmpty){
			return
		}

		val manager: Option[GuildMusicManager] = this.getManager(guild)
		if (manager.isEmpty){
			return
		}
	}
}
