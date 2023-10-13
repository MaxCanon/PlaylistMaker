package com.example.playlistmaker.media.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.favorites.ui.FavoritesFragment
import com.example.playlistmaker.playlists.ui.PlaylistsFragment

class MediaPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        if (position == 0) FavoritesFragment.newInstance() else PlaylistsFragment.newInstance()
}