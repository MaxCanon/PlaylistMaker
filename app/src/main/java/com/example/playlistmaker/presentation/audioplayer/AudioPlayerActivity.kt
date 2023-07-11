package com.example.playlistmaker.presentation.audioplayer

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.presentation.app.App.Companion.TRACK
import com.example.playlistmaker.presentation.dateutils.DateUtils.formatTime
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.domain.models.Track


class AudioPlayerActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY_MILLIS_MS = 1000L
    }

    private val mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable by lazy {
        Runnable {
            binding.durationTrackPlay.text = formatTime(mediaPlayer.currentPosition.toLong())
            handler.postDelayed(runnable, DELAY_MILLIS_MS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val track = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TRACK, Track::class.java)
        } else {
            intent.getParcelableExtra(TRACK)
        } as Track

        goToPlayer(track)
        preparePlayer(track.previewUrl)

        binding.playButton.setOnClickListener {
            playbackControl()
        }
        binding.durationTrackPlay.setText(R.string.time_030)

    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }

    private fun goToPlayer(track: Track) = with(binding) {
        trackName.text = track.trackName
        artistName.text = track.artistName
        trackDuration.text = formatTime(track.trackTimeMillis)
        if (track.collectionName.isNullOrEmpty()) {
            trackAlbum.visibility = View.GONE
            album.visibility = View.GONE
        } else {
            trackAlbum.text = track.collectionName
        }

        trackYear.text = track.getReleaseDateOnlyYear()
        trackGenre.text = track.primaryGenreName
        trackCountry.text = track.country
        Glide.with(albumCover)
            .load(track.getCoverArtwork())
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.radius_album)))
            .into(albumCover)
    }

    private fun preparePlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            binding.playButton.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            binding.playButton.setImageResource(R.drawable.play_button)
            playerState = STATE_PREPARED
            binding.durationTrackPlay.setText(R.string.time_030)
            handler.removeCallbacks(runnable)

        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        binding.playButton.setImageResource(R.drawable.pause_button)
        playerState = STATE_PLAYING
        handler.post(runnable)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        binding.playButton.setImageResource(R.drawable.play_button)
        handler.removeCallbacks(runnable)
        playerState = STATE_PAUSED
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }
}