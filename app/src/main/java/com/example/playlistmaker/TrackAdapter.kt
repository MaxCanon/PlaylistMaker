package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(private val data: ArrayList<Track>) :
    RecyclerView.Adapter<TrackHolder>() {
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
        holder.bind(data[position])
    }


    override fun getItemCount(): Int {
        return data.size
    }
}