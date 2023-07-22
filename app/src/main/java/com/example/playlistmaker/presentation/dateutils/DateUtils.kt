package com.example.playlistmaker.presentation.dateutils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatTime(millis : Long) : String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(millis)
    }
}