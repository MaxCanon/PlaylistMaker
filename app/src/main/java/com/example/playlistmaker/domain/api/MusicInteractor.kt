package com.example.playlistmaker.domain.api

import com.example.playlistmaker.data.dto.TrackDto

interface MusicInteractor {
    fun searchMusic(songName: String, consumer: MusicConsumer)

    interface MusicConsumer {
        fun consume(foundMusic: ArrayList<TrackDto>)
    }
}