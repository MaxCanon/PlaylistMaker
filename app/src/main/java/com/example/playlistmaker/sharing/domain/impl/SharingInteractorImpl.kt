package com.example.playlistmaker.sharing.domain.impl

import com.example.playlistmaker.sharing.domain.interactor.SharingInteractor
import com.example.playlistmaker.sharing.domain.model.EmailData
import com.example.playlistmaker.sharing.domain.repository.ExternalNavigator

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
) : SharingInteractor {

    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        return APP_LINK
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData(mail = SUPPORT_EMAIL)
    }

    private fun getTermsLink(): String {
        return TERM_LINK
    }

    companion object {
        const val APP_LINK = "https://practicum.yandex.ru/android-developer"
        const val SUPPORT_EMAIL = "bizkitman@yandex.by"
        const val TERM_LINK = "https://yandex.ru/legal/practicum_offer"
    }
}