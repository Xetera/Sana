package music

import com.sedmelluq.discord.lavaplayer.player.{AudioLoadResultHandler, AudioPlayer}
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.{AudioPlaylist, AudioTrack}
import music.TrackScheduler
import net.dv8tion.jda.core.entities.TextChannel

class AudioHandler(player: AudioPlayer, trackScheduler: TrackScheduler, bound: TextChannel) extends AudioLoadResultHandler {

	override def loadFailed(exception: FriendlyException): Unit = {
		println("load failed")
	}

	override def playlistLoaded(playlist: AudioPlaylist): Unit = {
		println("playlist loaded")
	}

	override def noMatches(): Unit = {
		println("no matches")
	}

	override def trackLoaded(track: AudioTrack): Unit = {
		//trackScheduler.queue(track)
		bound.sendMessage(s"Now playing ${track.getInfo.title}").queue()
		player.setVolume(7)
		trackScheduler.queue(track)
		player.playTrack(track)
	}
}