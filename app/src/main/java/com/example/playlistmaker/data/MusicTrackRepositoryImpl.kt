package com.example.playlistmaker.data

import android.content.Context
import com.example.playlistmaker.data.dto.TrackDto
import com.example.playlistmaker.data.mapper.MusicTrackMapper
import com.example.playlistmaker.domain.api.MusicTrackRepository
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.app.App
import com.google.gson.Gson

class MusicTrackRepositoryImpl(context: Context): MusicTrackRepository {
    private val sharedPreferences = context.getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE)

    override fun getCurrentMusicTrack(): Track? {
        val jsonTrack = sharedPreferences.getString(App.CURRENT_PLAYING_TRACK, "")

        return if(jsonTrack.isNullOrEmpty()) null
        else {
            val jTrack = Gson().fromJson(jsonTrack, TrackDto::class.java)
            return MusicTrackMapper().mapFromDto(jTrack)
        }
    }

    override fun setCurrentMusicTrack(musicTrack: Track) {
        val musicTrackDto = MusicTrackMapper().mapToDto(musicTrack)
        val jsonTrack = Gson().toJson(musicTrackDto, TrackDto::class.java)
        sharedPreferences.edit().putString(App.CURRENT_PLAYING_TRACK, jsonTrack).apply()
    }
}