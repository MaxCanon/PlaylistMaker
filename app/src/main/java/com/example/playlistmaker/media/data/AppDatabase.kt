package com.example.playlistmaker.media.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.media.data.dao.PlaylistDao
import com.example.playlistmaker.media.data.dao.PlaylistTrackDao
import com.example.playlistmaker.media.data.dao.TrackDao
import com.example.playlistmaker.media.data.entity.PlaylistEntity
import com.example.playlistmaker.media.data.entity.PlaylistTrackEntity
import com.example.playlistmaker.media.data.entity.TrackEntity

@Database(version = 1, entities = [TrackEntity::class, PlaylistEntity:: class, PlaylistTrackEntity:: class])
abstract class AppDatabase: RoomDatabase() {

    abstract fun trackDao(): TrackDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun playlistTrackDao(): PlaylistTrackDao
}