package com.example.playlistmaker.utils

interface ResourceProvider {
    fun getString(resId: Int): String
}