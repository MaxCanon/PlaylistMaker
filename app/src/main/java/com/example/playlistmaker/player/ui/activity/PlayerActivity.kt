package com.example.playlistmaker.player.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.player.domain.PlayerState
import com.example.playlistmaker.player.ui.view_model.PlayerViewModel
import com.example.playlistmaker.search.domain.model.Track
import com.example.playlistmaker.util.TRACK
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class PlayerActivity : AppCompatActivity() {

    private lateinit var playerBinding: ActivityAudioPlayerBinding
    private val viewModel by viewModel<PlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerBinding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(playerBinding.root)

        initListeners()

        val track = intent.getSerializableExtra(TRACK) as Track
        track.previewUrl?.let { viewModel.prepare(it) }

        viewModel.observeState().observe(this) { state ->
            playerBinding.playButton.setOnClickListener {
                controller(state)
            }
            if (state == PlayerState.STATE_COMPLETE) {
                playerBinding.durationTrackPlay.text = getString(R.string.time_030)
                setPlayIcon()
            }
        }

        viewModel.checkIsFavourite(track.trackId)

        playerBinding.likeButton.setOnClickListener {
            viewModel.onFavouriteClicked(track)
        }

        viewModel.observeTime().observe(this) {
            playerBinding.durationTrackPlay.text = it
        }

        viewModel.observeIsFavourite().observe(this) { isFavorite ->
            playerBinding.likeButton.setImageResource(
                if (isFavorite) R.drawable.ic_like_button_favourite else R.drawable.like_button
            )
        }

        showTrack(track)
    }

    private fun showTrack(track: Track) {
        playerBinding.apply {
            Glide
                .with(albumCover)
                .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.radius_album)))
                .into(albumCover)
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackGenre.text = track.primaryGenreName
            trackCountry.text = track.country

            trackDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault())
                .format(track.trackTimeMillis)

            val date =
                track.releaseDate?.let {
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
                        it
                    )
                }
            if (date != null) {
                val formattedDatesString =
                    SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
                trackYear.text = formattedDatesString
            }


            if (track.collectionName.isNotEmpty()) {
                trackAlbum.text = track.collectionName
            } else {
                trackAlbum.visibility = View.GONE
                album.visibility = View.GONE
            }

        }
    }

    private fun controller(state: PlayerState) {
        when (state) {
            PlayerState.STATE_PREPARED, PlayerState.STATE_COMPLETE, PlayerState.STATE_PAUSED -> {
                viewModel.play()
                setPauseIcon()
            }

            PlayerState.STATE_PLAYING -> {
                viewModel.pause()
                setPlayIcon()
            }
        }
    }

    private fun initListeners() {
        playerBinding.toolbarInclude.toolbar.apply {
            title = ""
            setSupportActionBar(this)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setPlayIcon() {
        playerBinding.playButton.setImageResource(R.drawable.play_button)
    }

    private fun setPauseIcon() {
        playerBinding.playButton.setImageResource(R.drawable.pause_button)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.reset()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }
}