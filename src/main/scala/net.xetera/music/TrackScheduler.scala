package music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.{AudioEventAdapter, AudioEventListener}
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.{AudioTrack, AudioTrackEndReason}

class TrackScheduler(player: AudioPlayer) extends AudioEventAdapter with AudioEventListener{

	import java.util.concurrent.LinkedBlockingQueue

	val queue = new LinkedBlockingQueue[AnyRef]

	def queue(track: AudioTrack): Unit = { // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
		// something is playing, it returns false and does nothing. In that case the player was already playing so this
		// track goes to the queue instead.
		if (!player.startTrack(track, true)) {
			queue.offer(track)
		}
		else {
			println("Could not start track")
		}

	}

	override def onPlayerPause(player: AudioPlayer): Unit = {
		println("Paused player")
	}

	override def onPlayerResume(player: AudioPlayer): Unit = {
		println("Resumed player")
	}

	override def onTrackStart(player: AudioPlayer, track: AudioTrack): Unit = {
		// A track started playing
		println(s"Started new track ${track.getIdentifier}")
	}

	override def onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason): Unit = {
		if (endReason.mayStartNext) {
			// Start next track
		}
		// endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
		// endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
		// endReason == STOPPED: The player was stopped.
		// endReason == REPLACED: Another track started playing while this had not finished
		// endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
		//                       clone of this back to your queue
	}

	override def onTrackException(player: AudioPlayer, track: AudioTrack, exception: FriendlyException): Unit = {
		// An already playing track threw an exception (track end event will still be received separately)
	}

	override def onTrackStuck(player: AudioPlayer, track: AudioTrack, thresholdMs: Long): Unit = {
		// Audio track has been unable to provide us any audio, might want to just start a new track
	}

}