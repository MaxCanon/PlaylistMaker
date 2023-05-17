package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {
            addTrack(trackList[position])
        }
    }


    override fun getItemCount(): Int {
        return trackList.size
    }
}