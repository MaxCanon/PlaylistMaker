package com.example.playlistmaker.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.presentation.app.App.Companion.TRACK
import com.example.playlistmaker.R
import com.example.playlistmaker.data.SearchRepository
import com.example.playlistmaker.data.dto.TracksSearchResponse
import com.example.playlistmaker.domain.network.ItunesApi
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.app.App
import com.example.playlistmaker.presentation.audioplayer.AudioPlayerActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var recycleViewTracks: RecyclerView
    private lateinit var searchHistory: SearchRepository
    private var text: String = ""
    private val baseUrl = "http://itunes.apple.com"
    private val trackList = ArrayList<Track>()
    private val interceptor = HttpLoggingInterceptor()
    private var historyList = ArrayList<Track>()


    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl).client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackApi = retrofit.create(ItunesApi::class.java)

    companion object {
        const val EDIT_TEXT = "EDIT_TEXT"
        private const val CLICK_DEBOUNCE_DELAY_ML = 1000L
        private const val SEARCH_DEBOUNCE_DELAY_ML = 2000L
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable { search() }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        binding.inputEditText.setOnFocusChangeListener { v, hasFocus ->
            focusVisibility(hasFocus)
        }

        binding.inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            }
            false
        }

        binding.searchToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.clearIcon.setOnClickListener {
            binding.inputEditText.setText("")
            closeKeyboard(binding.inputEditText)
            trackList.clear()
            binding.placeholderMessage.visibility = View.GONE
            showHistory()
            trackAdapter.notifyDataSetChanged()

        }

        binding.buttonUpdate.setOnClickListener {
            search()
        }

        searchHistory = SearchRepository(applicationContext)
        trackAdapter = TrackAdapter {
            searchHistory.addTrack(it)
            if(clickDebounce()) {
                val intent = Intent(this, AudioPlayerActivity::class.java)
                intent.putExtra(TRACK, it)
                startActivity(intent)
            }
        }
        trackAdapter.trackList = trackList
        historyList.clear()
        historyList = fillInList()
        recycleViewTracks = findViewById(R.id.search_recycle_view)
        recycleViewTracks.adapter = trackAdapter

        binding.buttonClearHistory.setOnClickListener {
            clear()
            historyList.clear()
            hideButtons()
            trackAdapter.notifyDataSetChanged()
        }

        val simpleTextWatcher = binding.inputEditText.doOnTextChanged { text, _, _, _ ->
            this@SearchActivity.text = text.toString()
            if (!text.isNullOrEmpty()) {
                binding.clearIcon.visibility = View.VISIBLE
                searchDebounce()
                history()
            } else {
                binding.clearIcon.visibility = View.GONE
            }
        }
        binding.inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY_ML)
        }
        return current
    }

    private fun hideButtons() {
        binding.searchHistory.visibility = View.GONE
        binding.buttonClearHistory.visibility = View.GONE
    }

    private fun history() {
        binding.searchHistory.visibility = View.GONE
        binding.buttonClearHistory.visibility = View.GONE
        trackAdapter.trackList = trackList
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun focusVisibility(hasFocus: Boolean) {
        if (hasFocus && binding.inputEditText.text.isEmpty() && historyList.isNotEmpty()) {
            binding.searchHistory.visibility = View.VISIBLE
            binding.buttonClearHistory.visibility = View.VISIBLE
        } else {
            binding.searchHistory.visibility = View.GONE
            binding.buttonClearHistory.visibility = View.GONE
        }
        trackAdapter.trackList = historyList
        trackAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showHistory() {
        historyList = fillInList()
        if (historyList.isNotEmpty()) {
            binding.searchHistory.visibility = View.VISIBLE
            binding.buttonClearHistory.visibility = View.VISIBLE
        } else {
            binding.searchHistory.visibility = View.GONE
            binding.buttonClearHistory.visibility = View.GONE
        }
        trackAdapter.trackList = historyList
        trackAdapter.notifyDataSetChanged()
    }

    private fun closeKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT, binding.inputEditText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        text = savedInstanceState.getString(EDIT_TEXT).toString()
        binding.inputEditText.setText(text)
    }

    @SuppressLint("WrongViewCast", "NotifyDataSetChanged")
    private fun showMessage(text: String, button: Boolean) {
        if (text.isNotEmpty()) {
            binding.placeholderMessage.visibility = View.VISIBLE
            binding.noConnection.visibility = View.GONE
            binding.nothingFoundImage.visibility = View.VISIBLE
            trackList.clear()
            trackAdapter.notifyDataSetChanged()
            binding.tvError.text = text
            if (button) {
                binding.nothingFoundImage.visibility = View.GONE
                binding.noConnection.visibility = View.VISIBLE
                binding.buttonUpdate.visibility = View.VISIBLE
            } else {
                binding.buttonUpdate.visibility = View.GONE
            }
        } else {
            binding.placeholderMessage.visibility = View.GONE
        }
    }
    private fun clear() {
        App.sharedMemory.edit()
            .remove(SearchRepository.HISTORY)
            .apply()
    }
    private fun fillInList(): ArrayList<Track> {
        var historyList = ArrayList<Track>()
        val getShare = App.sharedMemory.getString(SearchRepository.HISTORY, null)
        if (!getShare.isNullOrEmpty()) {
            val sType = object : TypeToken<ArrayList<Track>>() {}.type
            historyList = Gson().fromJson(getShare, sType)
        }
        return historyList
    }
    private fun searchDebounce() {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, SEARCH_DEBOUNCE_DELAY_ML)
    }

    private fun search() {
        if (binding.inputEditText.text.isNotEmpty()) {
            binding.progressBar.visibility = View.VISIBLE
            binding.searchRecycleView.visibility = View.GONE
            binding.nothingFoundImage.visibility = View.GONE
            trackApi.search(binding.inputEditText.text.toString())
                .enqueue(object : Callback<TracksSearchResponse> {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onResponse(
                        call: Call<TracksSearchResponse>,
                        response: Response<TracksSearchResponse>
                    ) {
                        Log.d("TRACK", "onResponse $response")
                        binding.progressBar.visibility = View.GONE
                        binding.searchRecycleView.visibility = View.VISIBLE
                        if (response.code() == 200) {
                            trackList.clear()
                        }
                        if (response.body()?.results?.isNotEmpty() == true) {
                            trackList.addAll(response.body()?.results!!)
                            trackAdapter.notifyDataSetChanged()
                        }
                        if (trackList.isEmpty()) {
                            showMessage(
                                getString(R.string.nothing_was_found),
                                false
                            )
                            binding.progressBar.visibility = View.GONE
                            binding.nothingFoundImage.visibility = View.VISIBLE
                        } else {
                            showMessage("", false)
                        }
                    }

                    override fun onFailure(call: Call<TracksSearchResponse>, t: Throwable) {
                        showMessage(
                            getString(R.string.connection_problem),
                            true
                        )
                        binding.progressBar.visibility = View.GONE
                    }
                })
        }
    }

}