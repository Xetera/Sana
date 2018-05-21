package music

import net.dv8tion.jda.core.audio.AudioSendHandler

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame

class AudioPlayerSendHandler(val audioPlayer: AudioPlayer) extends AudioSendHandler {
	private var lastFrame: AudioFrame = _

	override def canProvide: Boolean = {
		this.lastFrame = this.audioPlayer.provide()
		this.lastFrame != null
	}

	override def provide20MsAudio: Array[Byte] = {
		if (this.lastFrame == null){
			this.lastFrame = audioPlayer.provide()
		}
		val data: Array[Byte] = this.lastFrame.data
		this.lastFrame = null
		data
	}

	override def isOpus = true
}
