package com.example.playlistmaker.settings.ui.fragment



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import com.example.playlistmaker.settings.ui.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        viewModel.themeSettingsState.observe(viewLifecycleOwner) { themeSettings ->
            binding.switchTheme.isChecked = themeSettings.darkTheme
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
        binding.switchTheme.setOnCheckedChangeListener { _, checked ->
            viewModel.switchTheme(checked)
        }
    }
}