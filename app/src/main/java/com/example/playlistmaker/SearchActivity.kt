package com.example.playlistmaker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar

class SearchActivity : AppCompatActivity() {
    companion object {
        const val EDIT_TEXT = "EDIT_TEXT"
    }
    var text:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar = findViewById<Toolbar>(R.id.search_toolbar)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val inputEditText = findViewById<EditText>(R.id.input_edit_text)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            inputEditText.setText("")
            closeKeyboard(inputEditText)

        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)


            }

            override fun afterTextChanged(s: Editable?) {
                //
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
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
}