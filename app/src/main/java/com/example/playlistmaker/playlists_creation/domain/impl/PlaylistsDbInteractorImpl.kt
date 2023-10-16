package com.example.playlistmaker.playlists_creation.domain.impl

import com.example.playlistmaker.playlists_creation.domain.api.db.PlaylistsDbInteractor
import com.example.playlistmaker.playlists_creation.domain.api.db.PlaylistsRepository
import com.example.playlistmaker.playlists_creation.domain.model.Playlist
import com.example.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

class PlaylistsDbInteractorImpl(private val repository: PlaylistsRepository) :
    PlaylistsDbInteractor {
    override suspend fun addPlaylist(playlist: Playlist) {
        repository.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        repository.deletePlaylist(playlist)
    }

    override fun getPlaylists(): Flow<List<Playlist>> = repository.getPlaylists()

    override suspend fun addTrackToPl(track: Track, playlist: Playlist) {
        repository.addTrackToPl(track, playlist)
    }
}