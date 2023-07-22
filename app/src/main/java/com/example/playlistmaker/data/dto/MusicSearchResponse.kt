package com.example.playlistmaker.data.dto

data class MusicSearchResponse(
    val resultCount: Int,
    val results: ArrayList<TrackDto>
):Response()