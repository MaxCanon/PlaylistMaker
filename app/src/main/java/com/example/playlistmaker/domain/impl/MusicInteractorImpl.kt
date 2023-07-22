package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.MusicInteractor
import com.example.playlistmaker.domain.api.MusicRepository

class MusicInteractorImpl(private val musicRepository: MusicRepository):MusicInteractor {

    private val executor = java.util.concurrent.Executors.newCachedThreadPool()

    override fun searchMusic(songName: String, consumer: MusicInteractor.MusicConsumer) {
        executor.execute {
            consumer.consume(musicRepository.searchMusic(songName))
        }
    }
}