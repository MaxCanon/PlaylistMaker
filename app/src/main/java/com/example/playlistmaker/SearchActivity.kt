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
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_search)

        val toolbar = findViewById<Toolbar>(R.id.search_toolbar)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val inputEditText = findViewById<EditText>(R.id.input_edit_text)
        val updateButton = findViewById<Button>(R.id.buttonUpdate)
        val clearHistoryButton = findViewById<Button>(R.id.buttonClearHistory)


        interceptor.level = HttpLoggingInterceptor.Level.BODY

        inputEditText.setOnFocusChangeListener { v, hasFocus ->
            focusVisibility(hasFocus)
        }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            }
            false
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            val placeholder = findViewById<LinearLayout>(R.id.placeholderMessage)
            inputEditText.setText("")
            closeKeyboard(inputEditText)
            trackList.clear()
            placeholder.visibility = View.GONE
            showHistory()
            trackAdapter.notifyDataSetChanged()

        }

        updateButton.setOnClickListener {
            search()
        }

        trackAdapter = TrackAdapter()
        trackAdapter.trackList = trackList
        historyList.clear()
        historyList = SearchHistory.fillInList()
        recycleViewTracks = findViewById(R.id.search_recycle_view)
        recycleViewTracks.adapter = trackAdapter

        clearHistoryButton.setOnClickListener {
            SearchHistory.clear()
            historyList.clear()
            hideButtons()
            trackAdapter.notifyDataSetChanged()
        }

        val simpleTextWatcher = inputEditText.doOnTextChanged { text, _, _, _ ->
            this@SearchActivity.text = text.toString()
            if (!text.isNullOrEmpty()) {
                clearButton.visibility = View.VISIBLE
                history()
            } else {
                clearButton.visibility = View.GONE
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    private fun hideButtons() {
        val srcHistory = findViewById<TextView>(R.id.searchHistory)
        val clear = findViewById<Button>(R.id.buttonClearHistory)
        srcHistory.visibility = View.GONE
        clear.visibility = View.GONE
    }

    private fun history() {
        val srcHistory = findViewById<TextView>(R.id.searchHistory)
        val clear = findViewById<Button>(R.id.buttonClearHistory)
        srcHistory.visibility = View.GONE
        clear.visibility = View.GONE
        trackAdapter.trackList = trackList
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun focusVisibility(hasFocus: Boolean) {
        val srcHistory = findViewById<TextView>(R.id.searchHistory)
        val clear = findViewById<Button>(R.id.buttonClearHistory)
        val inputEditText = findViewById<EditText>(R.id.input_edit_text)
        if (hasFocus && inputEditText.text.isEmpty() && historyList.isNotEmpty()) {
            srcHistory.visibility = View.VISIBLE
            clear.visibility = View.VISIBLE
        } else {
            srcHistory.visibility = View.GONE
            clear.visibility = View.GONE
        }
        trackAdapter.trackList = historyList
        trackAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showHistory() {
        val srcHistory = findViewById<TextView>(R.id.searchHistory)
        val clear = findViewById<Button>(R.id.buttonClearHistory)
        if (historyList.isNotEmpty()) {
            srcHistory.visibility = View.VISIBLE
            clear.visibility = View.VISIBLE
        } else {
            srcHistory.visibility = View.GONE
            clear.visibility = View.GONE
        }
        historyList = SearchHistory.fillInList()
        trackAdapter.trackList = historyList
        trackAdapter.notifyDataSetChanged()
    }

    private fun closeKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val inputEditText = findViewById<EditText>(R.id.input_edit_text)
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT, inputEditText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val inputEditText = findViewById<EditText>(R.id.input_edit_text)
        super.onRestoreInstanceState(savedInstanceState)
        text = savedInstanceState.getString(EDIT_TEXT).toString()
        inputEditText.setText(text)
    }

    @SuppressLint("WrongViewCast", "NotifyDataSetChanged")
    private fun showMessage(text: String, button: Boolean) {
        val placeholder = findViewById<LinearLayout>(R.id.placeholderMessage)
        val noConnection = findViewById<ImageView>(R.id.noConnection)
        val nothingFoundImage = findViewById<ImageView>(R.id.nothingFoundImage)
        val tvError = findViewById<TextView>(R.id.tvError)
        val buttonUpdate = findViewById<Button>(R.id.buttonUpdate)

        if (text.isNotEmpty()) {
            placeholder.visibility = View.VISIBLE
            noConnection.visibility = View.GONE
            nothingFoundImage.visibility = View.VISIBLE
            trackList.clear()
            trackAdapter.notifyDataSetChanged()
            tvError.text = text
            if (button) {
                nothingFoundImage.visibility = View.GONE
                noConnection.visibility = View.VISIBLE
                buttonUpdate.visibility = View.VISIBLE
            } else {
                buttonUpdate.visibility = View.GONE
            }
        } else {
            placeholder.visibility = View.GONE
        }
    }

    private fun search() {
        val inputEditText = findViewById<EditText>(R.id.input_edit_text)

        if (inputEditText.text.isNotEmpty()) {
            trackApi.search(inputEditText.text.toString())
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