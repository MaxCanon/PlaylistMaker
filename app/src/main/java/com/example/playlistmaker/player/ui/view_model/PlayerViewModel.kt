package com.example.playlistmaker.player.ui.view_model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.player.domain.PlayerState
import com.example.playlistmaker.player.domain.interactor.PlayerInteractor
import com.example.playlistmaker.util.App.Companion.formatTime
import com.example.playlistmaker.util.Creator

class PlayerViewModel(val playerInteractor: PlayerInteractor) : ViewModel() {

    private val _stateLiveData = MutableLiveData<PlayerState>()
    fun observeState(): LiveData<PlayerState> = _stateLiveData

    private val _timeLiveData = MutableLiveData<String>()
    fun observeTime(): LiveData<String> = _timeLiveData
    private val handler = Handler(Looper.getMainLooper())

    private val time = object : Runnable {
        override fun run() {
            val position = playerInteractor.getPosition()
            _timeLiveData.postValue(position.formatTime())
            handler.postDelayed(this, DELAY_MILLIS)
        }
    }

    init {
        playerInteractor.setOnStateChangeListener { state ->
            _stateLiveData.postValue(state)
            if (state == PlayerState.STATE_COMPLETE) handler.removeCallbacks(time)
        }
    }


    fun prepare(url: String) {
        handler.removeCallbacks(time)
        playerInteractor.preparePlayer(url)
    }

    fun play() {
        playerInteractor.startPlayer()
        handler.post(time)
    }

    fun pause() {
        playerInteractor.pausePlayer()
        handler.removeCallbacks(time)
    }

    fun release() {
        playerInteractor.release()
        handler.removeCallbacks(time)
    }

    companion object {
        private const val DELAY_MILLIS = 1000L
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerViewModel(Creator.providePlayerInteractor())
            }
        }
    }
}