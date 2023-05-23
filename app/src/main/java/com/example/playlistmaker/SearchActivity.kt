package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.ActivitySearchBinding
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

    private val trackApi = retrofit.create(TrackApi::class.java)

    companion object {
        const val EDIT_TEXT = "EDIT_TEXT"
    }

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

        trackAdapter = TrackAdapter()
        trackAdapter.trackList = trackList
        historyList.clear()
        historyList = SearchHistory.fillInList()
        recycleViewTracks = findViewById(R.id.search_recycle_view)
        recycleViewTracks.adapter = trackAdapter

        binding.buttonClearHistory.setOnClickListener {
            SearchHistory.clear()
            historyList.clear()
            hideButtons()
            trackAdapter.notifyDataSetChanged()
        }

        val simpleTextWatcher = binding.inputEditText.doOnTextChanged { text, _, _, _ ->
            this@SearchActivity.text = text.toString()
            if (!text.isNullOrEmpty()) {
                binding.clearIcon.visibility = View.VISIBLE
                history()
            } else {
                binding.clearIcon.visibility = View.GONE
            }
        }
        binding.inputEditText.addTextChangedListener(simpleTextWatcher)
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
        historyList = SearchHistory.fillInList()
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

    private fun search() {
        if (binding.inputEditText.text.isNotEmpty()) {
            trackApi.search(binding.inputEditText.text.toString())
                .enqueue(object : Callback<TrackResponse> {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onResponse(
                        call: Call<TrackResponse>,
                        response: Response<TrackResponse>
                    ) {
                        Log.d("TRACK", "onResponse $response")
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
                        } else {
                            showMessage("", false)
                        }
                    }

                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        showMessage(
                            getString(R.string.connection_problem),
                            true
                        )
                    }
                })
        }
    }
}