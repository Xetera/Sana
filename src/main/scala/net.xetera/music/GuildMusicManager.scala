package music

import com.sedmelluq.discord.lavaplayer.player.{AudioPlayer, AudioPlayerManager, DefaultAudioPlayerManager}
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.core.entities.{Guild, TextChannel, VoiceChannel}
import net.dv8tion.jda.core.managers.AudioManager

class GuildMusicManager(guild: Guild, channel: VoiceChannel, bound: TextChannel) {
	/**
	  * Configures the player
	  */
	val playerManager: AudioPlayerManager = new DefaultAudioPlayerManager()
	/**
	  * Registering the player manager as an audio source
	  */
	AudioSourceManagers.registerRemoteSources(playerManager)
	/**
	  * New single-guild player
	  */
	val player: AudioPlayer = playerManager.createPlayer
	/**
	  * JDA connection of the music player
	  */
	val manager: AudioManager = guild.getAudioManager
	/**
	  * Registering the audio handler on discord
	  */
	manager.setSendingHandler(new AudioPlayerSendHandler(player))
	manager.openAudioConnection(channel)
	/**
	  * Queue and event listener for music updates
	  */
	val trackScheduler: TrackScheduler = new TrackScheduler(player)
	player.addListener(trackScheduler)

	val audioHandler = new AudioHandler(player, trackScheduler, bound)

	bound.sendMessage(s"Joining ${channel.getName}, sending updates to ${bound.getName}.").queue()

	def setVolume(from: TextChannel, amount: Int): Unit = {
		amount match {
			case _ if amount > 100 => from.sendMessage(s"**$amount** Must be a value between 0 - 100").queue()
			case _ if amount < 0 => from.sendMessage(s"**$amount** Must be a value between 0 - 100").queue()
			case _ if amount == 0 =>
				from.sendMessage("Paused.").queue()
				player.setPaused(true)
			case _ if amount > 0 && amount < 100 =>
				player.setVolume(((amount * 1.5) / 150).toInt)
				from.sendMessage(s"Set volume to $amount").queue()

			case any =>
				from.sendMessage(s"$any is not an valid number").queue()
		}
	}

	def changeChannels(from: TextChannel, to: VoiceChannel): Unit = {

	}

	def getStatus(from: TextChannel): Unit = {
		val isPlaying = player.getPlayingTrack

		if (player.isPaused || isPlaying == null){
			from.sendMessage("Paused").queue()
			return
		}
		//println(isPlaying)
		from.sendMessage(isPlaying.getUserData.toString).queue()
	}

	def play(from: TextChannel, args: String): Unit = {
		playerManager.loadItem(args, audioHandler)
	}

	def resume(from: TextChannel): Unit = {
		if (!player.isPaused){
			from.sendMessage("Player isn't paused.").queue()
			return
		}
		player.setPaused(false)
		from.sendMessage("Player is now playing.").queue()
	}

	def pause(from: TextChannel): Unit = {
		if (player.isPaused){
			from.sendMessage("Player is already paused.").queue()
			return
		}
		player.setPaused(true)
		from.sendMessage("Player is now paused.").queue()
	}
}
