package com.example.playlistmaker.utils

import com.example.playlistmaker.playlist_creation.domain.model.Playlist
import com.example.playlistmaker.search.domain.model.Track
import com.example.playlistmaker.utils.DateUtils.formatTime

object TextUtils {

    fun getHighResArtwork(midResArtworkUri: String) =
        midResArtworkUri.replaceAfterLast('/', "512x512bb.jpg")

    fun getLowResArtwork(midResArtworkUri: String) =
        midResArtworkUri.replaceAfterLast('/', "60x60bb.jpg")

    fun getSharedTracksString(playlist: Playlist, tracks: ArrayList<Track>, numberOfTracks: String): String {
        val tracksString = buildString {
            append(playlist.name)
            appendLine()
            append(playlist.description)
            appendLine()
            append(numberOfTracks)
            for (i in 0 until tracks.size) {
                appendLine()
                append("${i + 1}. ${tracks[i].artistName} - ${tracks[i].trackName} ${
                    formatTime(
                        tracks[i].duration
                    )
                }")
            }
        }
        return tracksString
    }
}