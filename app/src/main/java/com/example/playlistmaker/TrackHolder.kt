package com.example.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.DateUtils.formatTime

class TrackHolder(private val item: View) : RecyclerView.ViewHolder(item) {
    private var imageAlbum: ImageView = item.findViewById(R.id.album_image)
    private var trackName: TextView = item.findViewById(R.id.track_name)
    private var artistName: TextView = item.findViewById(R.id.artist_name)
    private var trackTime: TextView = item.findViewById(R.id.track_time)


    fun bind(musTrack: Track) {
        trackName.text = musTrack.trackName
        artistName.text = musTrack.artistName
        trackTime.text = formatTime(musTrack.trackTimeMillis)

        Glide
            .with(item.context)
            .load(musTrack.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(
                RoundedCorners(
                    itemView.resources
                        .getDimensionPixelOffset(R.dimen.image_radius)
                )
            )
            .into(imageAlbum)

    }
}