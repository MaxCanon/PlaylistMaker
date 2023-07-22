package com.example.playlistmaker.domain.api

import com.example.playlistmaker.data.dto.TrackDto

interface MusicRepository {
    fun searchMusic(songName:String):ArrayList<TrackDto>
}