package com.example.playlistmaker.media.domain.api

import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {

    suspend fun addPlaylist(playlist: Playlist): Long

    suspend fun deletePlaylist(playlist: Playlist)

    suspend fun getFlowPlaylists(): Flow<List<Playlist>>

    suspend fun addTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(track: Track, playlistId: Long)

    suspend fun getFlowPlaylistById(id: Long): Flow<Playlist?>

    suspend fun getPlaylistTracksByTrackIdList(trackIdList: List<Long>): List<Track>

    fun getPlaylistInfo(playlist: Playlist, trackList: List<Track>): String

    fun sharePlaylist(playlistInfo: String)
}