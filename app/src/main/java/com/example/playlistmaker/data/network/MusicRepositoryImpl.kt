package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.dto.MusicSearchRequest
import com.example.playlistmaker.data.dto.MusicSearchResponse
import com.example.playlistmaker.data.dto.TrackDto
import com.example.playlistmaker.domain.api.MusicRepository

class MusicRepositoryImpl(private val networkClient: NetworkClient): MusicRepository {
    override fun searchMusic(songName: String): ArrayList<TrackDto> {
        val response = networkClient.doRequest(MusicSearchRequest(songName))
        when(response.resultCode){
            200 ->{
                return (response as MusicSearchResponse).results
            }
            else -> return ArrayList()
        }
    }
}