package com.example.playlistmaker.playlist_details.domain.impl

import com.example.playlistmaker.playlist_details.domain.api.PlaylistInteractor
import com.example.playlistmaker.playlist_details.domain.api.PlaylistRepository
import com.example.playlistmaker.playlist_creation.domain.model.Playlist
import com.example.playlistmaker.search.domain.model.Track

class PlaylistInteractorImpl(private val repository: PlaylistRepository) : PlaylistInteractor {

    override suspend fun getTracksInPlaylist(playlist: Playlist): List<Track> =
        repository.getTracksInPlaylist(playlist)

    override suspend fun getPlaylist(playlistId: Int): Playlist =
        repository.getPlaylist(playlistId)

    override suspend fun deleteTrackFromPlaylist(trackId: Int, playlistId: Int) {
        repository.deleteTrackFromPlaylist(trackId, playlistId)
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        repository.deletePlaylist(playlistId)
    }
}