package com.example.playlistmaker.data

import android.content.Context
import androidx.core.content.edit
import com.example.playlistmaker.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchRepository(context: Context) {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val HISTORY = "history"
        const val SIZE = 10
    }
    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)


    fun fillInList(): ArrayList<Track> {
        val json = sharedPrefs.getString(HISTORY, null) ?: return ArrayList()
        val myType = object : TypeToken<ArrayList<Track>>() {}.type
        return Gson().fromJson(json, myType)
    }

    fun addTrack(track: Track) {
        val historyTrackList: ArrayList<Track> = fillInList()
        historyTrackList.remove(track)
        if (historyTrackList.size >= SIZE) {
            historyTrackList.removeAt(historyTrackList.size - 1)
        }
        historyTrackList.add(0, track)
        sharedPrefs.edit {
            putString(HISTORY, Gson().toJson(historyTrackList))
        }
    }

}