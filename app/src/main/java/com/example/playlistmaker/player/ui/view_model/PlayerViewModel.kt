package com.example.playlistmaker.player.ui.view_model

import androidx.lifecycle.*
import com.example.playlistmaker.player.domain.PlayerState
import com.example.playlistmaker.player.domain.interactor.PlayerInteractor
import com.example.playlistmaker.util.App.Companion.formatTime
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlayerViewModel(val playerInteractor: PlayerInteractor) : ViewModel() {

    private val _stateLiveData = MutableLiveData<PlayerState>()
    fun observeState(): LiveData<PlayerState> = _stateLiveData

    private val _timeLiveData = MutableLiveData<String>()
    fun observeTime(): LiveData<String> = _timeLiveData

    private var timerJob: Job? = null

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(DELAY_MILLIS)
                _timeLiveData.postValue(playerInteractor.getPosition().formatTime())
            }
        }
    }

    init {
        playerInteractor.setOnStateChangeListener { state ->
            _stateLiveData.postValue(state)
            if (state == PlayerState.STATE_COMPLETE) timerJob?.cancel()
        }
    }


    fun prepare(url: String) {
        timerJob?.cancel()
        playerInteractor.preparePlayer(url)
    }

    fun play() {
        playerInteractor.startPlayer()
        startTimer()
    }

    fun pause() {
        playerInteractor.pausePlayer()
        timerJob?.cancel()
    }

    fun release() {
        playerInteractor.reset()
        timerJob?.cancel()
    }

    companion object {
        private const val DELAY_MILLIS = 300L

    }
}