package com.example.playlistmaker.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Track

class TrackAdapter(private val clickListener: MovieClickListener) : RecyclerView.Adapter<TrackHolder>() {
    var trackList = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val item: View =
            LayoutInflater.from(parent.context).inflate(
                R.layout.track,
                parent,
                false
            )
        return TrackHolder(item)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val trackPosition  = trackList[position]
        holder.bind(trackPosition)
        holder.itemView.setOnClickListener {
            // addTrack(trackPosition)
            clickListener.onMovieClick(trackList[position])
        }
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun interface MovieClickListener {
        fun onMovieClick(track: Track)
    }
}