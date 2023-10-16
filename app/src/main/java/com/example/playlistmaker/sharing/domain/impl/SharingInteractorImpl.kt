package com.example.playlistmaker.sharing.domain.impl

import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.data.ExternalNavigator
import com.example.playlistmaker.sharing.domain.api.SharingInteractor
import com.example.playlistmaker.sharing.domain.model.EmailData
import com.example.playlistmaker.utils.ResourceProvider

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
    private val resourceProvider: ResourceProvider
) : SharingInteractor {

    override fun shareApp() {
        externalNavigator.shareString(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    override fun shareString(sharedTracksString: String) {
        externalNavigator.shareString(sharedTracksString)
    }
    private fun getShareAppLink() = resourceProvider.getString(R.string.practicum_android_link)

    private fun getSupportEmailData() = EmailData(
        email = resourceProvider.getString(R.string.feedback_addressee_mail),
        subject = resourceProvider.getString(R.string.feedback_subject),
        textMessage = resourceProvider.getString(R.string.feedback_message_text)
    )

    private fun getTermsLink() = resourceProvider.getString(R.string.practicum_term_link)
}