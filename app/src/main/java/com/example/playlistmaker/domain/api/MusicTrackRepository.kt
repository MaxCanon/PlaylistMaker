package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track

interface MusicTrackRepository {
    fun getCurrentMusicTrack(): Track?
    fun setCurrentMusicTrack(track:Track)
}