package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.api.ExternalNavigatorMedia
import com.example.playlistmaker.media.domain.api.PlaylistInteractor
import com.example.playlistmaker.media.domain.api.PlaylistRepository
import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.getTrackCountNoun
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val playlistRepository: PlaylistRepository,
    private val externalNavigatorMedia: ExternalNavigatorMedia
) :
    PlaylistInteractor {

    override suspend fun addPlaylist(playlist: Playlist): Long {
        return playlistRepository.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistRepository.deletePlaylist(playlist)
    }

    override suspend fun getFlowPlaylists(): Flow<List<Playlist>> {
        return playlistRepository.getFlowPlaylists()
    }

    override suspend fun addTrackToPlaylist(track: Track, playlistId: Long) {
        playlistRepository.addTrackToPlaylist(track, playlistId)
    }

    override suspend fun deleteTrackFromPlaylist(track: Track, playlistId: Long) {
        playlistRepository.deleteTrackFromPlaylist(track, playlistId)
    }

    override suspend fun getFlowPlaylistById(id: Long): Flow<Playlist?> {
        return playlistRepository.getFlowPlaylistById(id)
    }

    override suspend fun getPlaylistTracksByTrackIdList(trackIdList: List<Long>): List<Track> {
        return playlistRepository.getPlaylistTracksByTrackIdList(trackIdList)
    }

    override fun getPlaylistInfo(playlist: Playlist, trackList: List<Track>): String {
        var trackListInfo = ""
        trackList.forEachIndexed { index, track -> trackListInfo += "\n" + (index+1).toString() + "." + track.artistName + " - " + track.albumName }

        return playlist.name + "\n" + playlist.description + "\n" + playlist.trackCount + " " + getTrackCountNoun(
            playlist.trackCount
        ) + trackListInfo
    }

    override fun sharePlaylist(playlistInfo: String) {
        externalNavigatorMedia.sharePlaylist(playlistInfo)
    }
}