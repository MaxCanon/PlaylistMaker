package com.example.playlistmaker.domain.api

interface MusicPlayerInteractor {
    fun preparePlayer()
    fun playMusic()
    fun pauseMusic()
    fun turnOffPlayer()
}