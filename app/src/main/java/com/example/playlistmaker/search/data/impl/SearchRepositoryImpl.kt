package com.example.playlistmaker.search.data.impl

import com.example.playlistmaker.R
import com.example.playlistmaker.favorites.data.db.AppDatabase
import com.example.playlistmaker.search.data.LocalStorage
import com.example.playlistmaker.search.data.NetworkClient
import com.example.playlistmaker.search.data.api.SearchRepository
import com.example.playlistmaker.search.data.dto.SearchRequest
import com.example.playlistmaker.search.data.dto.SearchResponse
import com.example.playlistmaker.search.domain.model.Track
import com.example.playlistmaker.utils.DateUtils.formatTime
import com.example.playlistmaker.utils.DateUtils.getYear
import com.example.playlistmaker.utils.Resource
import com.example.playlistmaker.utils.ResourceProvider
import com.example.playlistmaker.utils.TextUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class SearchRepositoryImpl(
    private val localStorage: LocalStorage,
    private val appDatabase: AppDatabase,
    private val networkClient: NetworkClient,
    private val resourceProvider: ResourceProvider
) : SearchRepository, KoinComponent {

    override fun searchTracks(
        query: String
    ): Flow<Resource<List<Track>>> = flow {
        val searchRequest: SearchRequest = getKoin().get {
            parametersOf(query)
        }
        val response = networkClient.doRequest(searchRequest)
        when (response.resultCode) {
            NO_CONNECTIVITY_ERROR -> {
                emit(Resource.Error(resourceProvider.getString(R.string.no_internet_connection)))
            }

            SUCCESSFUL_SEARCH_CODE -> {
                val favoritesIds = appDatabase.favoritesDao().getFavoritesIds()
                emit(Resource.Success((response as SearchResponse).trackList.map {
                    Track(
                        it.trackId,
                        it.trackName,
                        it.artistName,
                        it.country,
                        it.releaseDate ?: "",
                        getYear(it.releaseDate) ?: "",
                        it.duration ?: 0,
                        TextUtils.getLowResArtwork(it.artworkUri),
                        it.artworkUri,
                        TextUtils.getHighResArtwork(it.artworkUri),
                        it.genre,
                        it.album,
                        it.previewUrl,
                        favoritesIds.contains(it.trackId)
                    )
                }))
            }

            else -> {
                emit(Resource.Error(resourceProvider.getString(R.string.server_error)))
            }
        }
    }

    override fun getSearchHistory(): Flow<List<Track>> = flow {
        val searchHistory = localStorage.getSearchHistory()
        val favoritesIds = appDatabase.favoritesDao().getFavoritesIds()
        emit(searchHistory.map {
            it.copy(isFavorite = favoritesIds.contains(it.trackId))
        })
    }

    override fun clearSearchHistory() {
        localStorage.clearSearchHistory()
    }

    override fun addTrackToSearchHistory(track: Track) {
        localStorage.addTrackToSearchHistory(track)
    }

    companion object {
        const val SUCCESSFUL_SEARCH_CODE = 200
        const val NO_CONNECTIVITY_ERROR = -1
    }
}