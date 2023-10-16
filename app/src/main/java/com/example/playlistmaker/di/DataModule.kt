package com.example.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.playlistmaker.favorites.data.db.AppDatabase
import com.example.playlistmaker.player.data.PlayerImpl
import com.example.playlistmaker.player.domain.api.Player
import com.example.playlistmaker.playlist_creation.data.local_files.PrivateStorage
import com.example.playlistmaker.playlist_creation.data.local_files.PrivateStorageImpl
import com.example.playlistmaker.search.data.LocalStorage
import com.example.playlistmaker.search.data.NetworkClient
import com.example.playlistmaker.search.data.dto.SearchRequest
import com.example.playlistmaker.search.data.network.ItunesService
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.search.data.sharedprefs.LocalStorageImpl
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    factoryOf(::PlayerImpl) bind Player::class
    singleOf(::LocalStorageImpl) bind LocalStorage::class
    singleOf(::RetrofitNetworkClient) bind NetworkClient::class
    factoryOf(::Gson)
    factoryOf(::MediaPlayer)
    factoryOf(::SearchRequest)
    singleOf(::PrivateStorageImpl) bind PrivateStorage::class

    single {
        val connectivityManager =
            androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    }

    single<ItunesService> {
        Retrofit.Builder()
            .baseUrl(RetrofitNetworkClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences("shared_preference", Context.MODE_PRIVATE)
    }



    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db").build()
    }

}