package com.example.playlistmaker.favorites.data

import com.example.playlistmaker.favorites.data.converters.TrackDbConverter
import com.example.playlistmaker.favorites.data.db.AppDatabase
import com.example.playlistmaker.favorites.data.db.entity.FavoritesTrackEntity
import com.example.playlistmaker.favorites.domain.api.FavoritesRepository
import com.example.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class FavoritesRepositoryImpl(
    private val database: AppDatabase,
    private val trackDbConverter: TrackDbConverter
) : FavoritesRepository {

    override suspend fun addFavorite(track: Track) {
        database.favoritesDao()
            .insertFavorite(trackDbConverter.map(track, Calendar.getInstance().time.time))
    }

    override suspend fun deleteFavorite(track: Track) {
        database.favoritesDao().deleteFavorite(trackDbConverter.map(track, Calendar.getInstance().time.time))
    }

    override fun getFavorites(): Flow<List<Track>> = flow {
        val tracks = database.favoritesDao().getFavorites()
        emit(convertFromTrackEntity(tracks))
    }

    private fun convertFromTrackEntity(tracks: List<FavoritesTrackEntity>): List<Track> =
        tracks.sortedByDescending { it.addingTime }.map { track -> trackDbConverter.map(track) }
}