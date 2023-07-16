package com.example.playlistmaker.presentation.audioplayer

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.presentation.app.App.Companion.TRACK
import com.example.playlistmaker.presentation.dateutils.DateUtils.formatTime
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.domain.impl.MusicPlayerInteractorImpl
import com.example.playlistmaker.data.MusicTrackRepositoryImpl
import com.example.playlistmaker.domain.models.Track
import java.text.SimpleDateFormat
import java.util.*


class AudioPlayerActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding

    lateinit var mediaPlayer : MusicPlayerInteractorImpl

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var durationRunnable: Runnable

    private fun setUiBehaviour() {
        this.durationRunnable = Runnable {
            changeDuration()
            handler.postDelayed(durationRunnable, 100)
        }

        binding.backButton.setOnClickListener { finish() }

        binding.playButton.setOnClickListener {
            if (mediaPlayer.isPlaying()) mediaPlayer.pauseMusic()
            else mediaPlayer.playMusic()
        }

    }

    private fun getFullDurationFromLong(duration: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(duration)
    }

    private fun showTrackInfo(track: Track): Boolean {
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val date = dateFormat.parse(track.releaseDate)
        val milliseconds = date.time
        val releaseYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(milliseconds)

        with(binding) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackAlbum.text = track.collectionName.toString()
            trackGenre.text = track.primaryGenreName
            trackCountry.text = track.country
            trackDuration.text = getFullDurationFromLong(track.trackTimeMillis)
            trackYear.text = releaseYear
        }

        val artWorkHQ = track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")

        val px = (this.baseContext.resources.displayMetrics.densityDpi
                / DisplayMetrics.DENSITY_DEFAULT)

        Glide
            .with(binding.root.context)
            .load(artWorkHQ)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.radius_album)))
            .into(binding.albumCover)

        return true
    }

    private fun changeBtnPlayPause(state: ButtonState) {
        if (state == ButtonState.BUTTON_PLAY) binding.playButton.setImageResource(R.drawable.play_button)
        else binding.playButton.setImageResource(R.drawable.pause_button)
    }

    private fun trackPlayingTimeUpdate(start: Boolean) {
        if (start) {
            handler.post(durationRunnable)
        } else {
            handler.removeCallbacks(durationRunnable)
        }
    }

    private fun changeDuration() {
        binding.durationTrackPlay.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.getCurrentPos())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val musTrackRepo = MusicTrackRepositoryImpl(binding.root.context)
        val currentTrack = musTrackRepo.getCurrentMusicTrack()
        if (currentTrack != null) this.showTrackInfo(currentTrack)


        val playerStateListener = MusicPlayerInteractorImpl.OnPlayerStateListener { playerState ->
            when (playerState) {
                MusicPlayerInteractorImpl.STATE_PREPARED -> changeBtnPlayPause(ButtonState.BUTTON_PLAY)
                MusicPlayerInteractorImpl.STATE_PLAYING -> {
                    changeBtnPlayPause(ButtonState.BUTTON_PAUSE)
                    trackPlayingTimeUpdate(true)
                }

                MusicPlayerInteractorImpl.STATE_COMPLETE -> changeBtnPlayPause(ButtonState.BUTTON_PLAY)
                MusicPlayerInteractorImpl.STATE_PAUSED -> {
                    changeBtnPlayPause(ButtonState.BUTTON_PLAY)
                    trackPlayingTimeUpdate(false)
                }
            }
        }
        binding.backButton.setOnClickListener {
            finish()
        }

        mediaPlayer = MusicPlayerInteractorImpl(musTrackRepo, playerStateListener)
        mediaPlayer.preparePlayer()

        setUiBehaviour()

        val track = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TRACK, Track::class.java)
        } else {
            intent.getParcelableExtra(TRACK)
        } as Track

        goToPlayer(track)

        binding.durationTrackPlay.setText(R.string.time_030)

    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pauseMusic()
        changeBtnPlayPause(ButtonState.BUTTON_PLAY)
        trackPlayingTimeUpdate(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(durationRunnable)
        mediaPlayer.turnOffPlayer()
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


    enum class ButtonState {
        BUTTON_PLAY,
        BUTTON_PAUSE
    }
}