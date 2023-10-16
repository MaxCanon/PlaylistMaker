package com.example.playlistmaker.favorites.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.favorites.data.db.dao.FavoritesDao
import com.example.playlistmaker.favorites.data.db.entity.TrackEntity
import com.example.playlistmaker.playlists_creation.data.db.dao.PlaylistTracksCrossRefDao
import com.example.playlistmaker.playlists_creation.data.db.dao.PlaylistsDao
import com.example.playlistmaker.playlists_creation.data.db.dao.TracksInPlDao
import com.example.playlistmaker.playlists_creation.data.db.entity.PlaylistEntity
import com.example.playlistmaker.playlists_creation.data.db.entity.PlaylistTracksCrossRef
import com.example.playlistmaker.playlists_creation.data.db.entity.TrackInPlEntity

@Database(
    version = 1,
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        TrackInPlEntity::class,
        PlaylistTracksCrossRef::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao
    abstract fun playlistsDao(): PlaylistsDao
    abstract fun tracksInPlDao(): TracksInPlDao
    abstract fun playlistsTracksCrossRefDao(): PlaylistTracksCrossRefDao
}