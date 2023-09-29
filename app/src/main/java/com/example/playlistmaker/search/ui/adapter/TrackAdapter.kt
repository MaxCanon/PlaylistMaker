package com.example.playlistmaker.search.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.search.domain.model.Track
import com.example.playlistmaker.search.ui.holder.TrackHolder
import com.example.playlistmaker.search.ui.tracksDiff.TracksDiffCallback

class TrackAdapter(private val clickListener: MovieClickListener) : RecyclerView.Adapter<TrackHolder>() {

    var tracks = mutableListOf<Track>()
        set(newTracks) {
            val diffCallback = TracksDiffCallback(field, newTracks)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newTracks
            diffResult.dispatchUpdatesTo(this)
        }

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
        val trackPosition  = tracks[position]
        holder.bind(trackPosition)
        holder.itemView.setOnClickListener {
            // addTrack(trackPosition)
            clickListener.onMovieClick(tracks[position])
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    fun interface MovieClickListener {
        fun onMovieClick(track: Track)
    }

    fun clearTracks () {
        tracks = ArrayList()
    }
}