package com.example.playlistmaker.media.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentFavoritesTracksBinding
import com.example.playlistmaker.media.viewmodel.FavoriteTracksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTracksFragment() : Fragment() {

    private var _binding : FragmentFavoritesTracksBinding? = null
    private val binding get() = _binding!!

    private val fragmentViewModel: FavoriteTracksViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesTracksBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        fun newInstance() =
            FavoriteTracksFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}