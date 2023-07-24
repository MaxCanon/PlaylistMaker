package com.example.playlistmaker.player.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.player.domain.PlayerState
import com.example.playlistmaker.player.ui.view_model.PlayerViewModel
import com.example.playlistmaker.search.domain.model.Track
import com.example.playlistmaker.util.App.Companion.TRACK
import com.example.playlistmaker.util.App.Companion.formatTime

class AudioPlayerActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding
    private lateinit var playerVIewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        playerVIewModel = ViewModelProvider(
            this, PlayerViewModel
                .getViewModelFactory()
        )[PlayerViewModel::class.java]


        val track =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(TRACK, Track::class.java)
            } else {
                intent.getParcelableExtra(TRACK)
            } as Track
        initViews(track)
        playerVIewModel.prepare(track.previewUrl)
        playerVIewModel.observeState().observe(this) { state ->
            binding.playButton.setOnClickListener {
                controller(state)
            }
            if (state == PlayerState.STATE_COMPLETE) {
                binding.durationTrackPlay.text = getString(R.string.time_030)
                pausePlayer()
            }
        }
        playerVIewModel.observeTime().observe(this) {
            binding.durationTrackPlay.text = it
        }
    }

    private fun initViews(track: Track) = with(binding) {
        trackName.text = track.trackName
        artistName.text = track.artistName
        trackDuration.text = track.trackTimeMillis.formatTime()
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

    private fun controller(state: PlayerState) {
        when (state) {
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED, PlayerState.STATE_COMPLETE -> {
                startPlayer()
            }

            PlayerState.STATE_PLAYING -> {
                pausePlayer()
            }
        }
    }

    private fun startPlayer() {
        binding.playButton.setImageResource(R.drawable.pause_button)
        playerVIewModel.play()
    }

    private fun pausePlayer() {
        binding.playButton.setImageResource(R.drawable.play_button)
        playerVIewModel.pause()
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerVIewModel.release()
    }
}