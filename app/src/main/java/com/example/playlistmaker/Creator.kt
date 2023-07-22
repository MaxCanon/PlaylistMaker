package com.example.playlistmaker

import com.example.playlistmaker.data.network.MusicRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.api.MusicInteractor
import com.example.playlistmaker.domain.api.MusicRepository
import com.example.playlistmaker.domain.impl.MusicInteractorImpl

object Creator {
    private fun getTracksRepository(): MusicRepository {
        return MusicRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): MusicInteractor {
        return MusicInteractorImpl(getTracksRepository())
    }
}