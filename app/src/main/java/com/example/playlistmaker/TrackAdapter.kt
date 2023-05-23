package com.example.playlistmaker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.App.Companion.TRACK
import com.example.playlistmaker.SearchHistory.addTrack

class TrackAdapter() : RecyclerView.Adapter<TrackHolder>() {
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
            addTrack(trackPosition)
            val intent = Intent(it.context, AudioPlayerActivity::class.java)
            intent.putExtra(TRACK, trackPosition)
            it.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return trackList.size
    }
}