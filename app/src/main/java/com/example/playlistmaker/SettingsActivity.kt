package com.example.playlistmaker


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.settings_toolbar)
        val share = findViewById<ImageView>(R.id.icon_share_btn)
        val support = findViewById<ImageView>(R.id.support_btn)
        val terms = findViewById<ImageView>(R.id.terms_btn)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        share.setOnClickListener {
            val url = getString(R.string.url_address)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(intent)
        }

        support.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_address)))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.message_to_develops_1))
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_to_develops_2))
            startActivity(shareIntent)
        }

        terms.setOnClickListener {
            val browser =
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.terms)))
            startActivity(browser)
        }
    }

}