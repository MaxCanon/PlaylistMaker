package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.api.LocalStorageInteractor
import java.io.File

class LocalStorageInteractorImpl(private val fileDir: File) : LocalStorageInteractor {
    override fun getImageDirectory() = fileDir
}