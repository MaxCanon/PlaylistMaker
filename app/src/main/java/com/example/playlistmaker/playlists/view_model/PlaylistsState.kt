package com.example.playlistmaker.playlists.view_model

import com.example.playlistmaker.playlist_creation.domain.model.Playlist

sealed interface PlaylistsState {
    object DisplayEmptyPlaylists : PlaylistsState
    class DisplayPlaylists(val playlists: List<Playlist>) : PlaylistsState
}