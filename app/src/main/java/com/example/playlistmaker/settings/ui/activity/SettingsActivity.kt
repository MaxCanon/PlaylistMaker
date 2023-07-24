package com.example.playlistmaker.settings.ui.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.settings.ui.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, SettingsViewModel.getViewModelFactory(this))[SettingsViewModel::class.java]

        initListeners()

        binding.settingsToolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        viewModel.themeSettingsState.observe(this) { themeSettings ->
            binding.nightTheme.isChecked = themeSettings.darkTheme
        }
    }

    private fun initListeners() {
        binding.iconShareBtn.setOnClickListener {
            viewModel.shareApp()
        }
        binding.supportBtn.setOnClickListener {
            viewModel.supportEmail()
        }
        binding.termsBtn.setOnClickListener {
            viewModel.openAgreement()
        }
        binding.nightTheme.setOnCheckedChangeListener { _, checked ->
            viewModel.switchTheme(checked)
        }
    }
}