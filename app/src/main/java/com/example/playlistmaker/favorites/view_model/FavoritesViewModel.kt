package com.example.playlistmaker.favorites.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.favorites.domain.api.FavoritesInteractor
import com.example.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.launch

class FavoritesViewModel(private val interactor: FavoritesInteractor) :
    ViewModel() {

    private val favoritesStateLiveData = MutableLiveData<FavoritesState>()
    fun observeFavoritesState(): LiveData<FavoritesState> = favoritesStateLiveData

    fun displayState() {
        fillData()
    }

    private fun fillData() {
        viewModelScope.launch {
            interactor.getFavorites().collect { tracks ->
                processResult(tracks)
            }
        }
    }

    private fun processResult(tracks: List<Track>) {
        if (tracks.isEmpty()) favoritesStateLiveData.postValue(FavoritesState.EmptyFavorites)
        else favoritesStateLiveData.postValue(FavoritesState.DisplayFavorites(tracks))
    }
}