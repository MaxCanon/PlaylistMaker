package com.example.playlistmaker.media.presentation.models

import com.example.playlistmaker.media.domain.models.Playlist

sealed interface PlaylistsScreenState {

    object Loading : PlaylistsScreenState

    object Empty : PlaylistsScreenState

    data class Content(val playlists: List<Playlist>) : PlaylistsScreenState
}