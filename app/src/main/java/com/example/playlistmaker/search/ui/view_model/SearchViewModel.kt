package com.example.playlistmaker.search.ui.view_model

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.search.domain.interactor.SearchInteractor
import com.example.playlistmaker.search.domain.model.Track
import com.example.playlistmaker.search.ui.model.SearchState

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {

    private val historyList = ArrayList<Track>()
    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true

    private val _stateLive = MutableLiveData<SearchState>()
    val stateLiveData: LiveData<SearchState> = _stateLive

    private var latestSearchText: String? = null

    private val _clickLiveData = MutableLiveData<Boolean>()
    fun observeClick(): LiveData<Boolean> = _clickLiveData

    init {
        historyList.addAll(searchInteractor.getHistory())
        _stateLive.postValue(SearchState.SearchHistory(historyList))
    }

    override fun onCleared() {
        super.onCleared()
        searchInteractor.saveHistory(historyList)
    }

    fun search(query: String) {
        if (query.isEmpty()) return

        _stateLive.postValue(SearchState.Loading)

        searchInteractor.searchTracks(query,
            onSuccess = { trackList ->
                _stateLive.postValue(SearchState.SearchedTracks(trackList))
            },
            onError = { error ->
                _stateLive.postValue(SearchState.SearchError(error))
            }
        )
    }

    fun clearHistory() {
        historyList.clear()
        _stateLive.postValue(SearchState.SearchHistory(historyList))
    }

    fun clearSearchText() {
        _stateLive.postValue(SearchState.SearchHistory(historyList))
    }

    fun addTrackToHistory(track: Track) {
        if (historyList.contains(track)) {
            historyList.removeAt(historyList.indexOf(track))
        } else if (historyList.size == maxHistorySize) {
            historyList.removeAt(0)
        }
        historyList.add(track)
        searchInteractor.saveHistory(historyList)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { search(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY_MILLIS
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY_MILLIS)
        }
        return current
    }

    companion object {
        private const val maxHistorySize = 10
        private val SEARCH_REQUEST_TOKEN = Any()
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L

    }
}