package com.example.playlistmaker.sharing.data

import com.example.playlistmaker.sharing.domain.model.EmailData

interface ExternalNavigator {
    fun shareString(stringToShare: String)
    fun openLink(termsLink: String)
    fun openEmail(supportEmailData: EmailData)
}