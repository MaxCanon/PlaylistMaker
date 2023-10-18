package com.example.playlistmaker.player.presentation.ui

import android.net.Uri
import android.os.Environment
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistListItemBinding
import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.media.presentation.ui.playlist.PlaylistFragment
import com.example.playlistmaker.utils.getTrackCountNoun
import java.io.File

class BottomSheetViewHolder(private val binding: PlaylistListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(model: Playlist) {
        binding.playlistListItemName.text = model.name.toString()
        binding.playlistListItemTrackCount.text = String.format("%d %s", model.trackCount, getTrackCountNoun(model.trackCount))

        binding.playlistListItemImage.setImageResource(R.drawable.ic_playlist)
        if (!model.filePath.isNullOrEmpty()) {
            val filePath = File(itemView.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), PlaylistFragment.IMAGE_DIR)
            val file = File(filePath, model.filePath!!)
            binding.playlistListItemImage.setImageURI(Uri.fromFile(file))
            binding.playlistListItemImage.clipToOutline = true
        }
    }
}