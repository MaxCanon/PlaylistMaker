package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.dto.Response
import com.example.playlistmaker.data.dto.MusicSearchRequest
import com.example.playlistmaker.data.dto.MusicSearchResponse
import com.example.playlistmaker.data.dto.TrackDto
import com.example.playlistmaker.presentation.msgcode.Msgcode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient: NetworkClient {

    private val baseUrl = "http://itunes.apple.com"
    var trackLst: ArrayList<TrackDto>? = null

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val mediaApi : ItunesApi = retrofit.create(ItunesApi::class.java)

    fun search(songName: String, doAfterSearch: (Msgcode) -> Unit) {
        val call = mediaApi.search(songName)
        call.enqueue(object : Callback<MusicSearchResponse> {
            override fun onResponse(
                call: Call<MusicSearchResponse>,
                response: retrofit2.Response<MusicSearchResponse>
            ) {
                if (response.code() == 200) {
                    trackLst = response.body()?.results
                    doAfterSearch.invoke(Msgcode.OK)
                } else doAfterSearch.invoke(Msgcode.Failure)
            }

            override fun onFailure(call: Call<MusicSearchResponse>, t: Throwable) {
                doAfterSearch.invoke(Msgcode.Failure)
            }
        })
    }


    override fun doRequest(dto: Any): com.example.playlistmaker.data.dto.Response {
        if(dto is MusicSearchRequest){
            val response = mediaApi.search(dto.songName).execute()
            val body = response.body() ?: Response()

            body.resultCode = response.code()
            return body
        }
        else{
            return Response().apply { resultCode = 400 }
        }

    }
}