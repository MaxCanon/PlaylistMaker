package com.example.playlistmaker.sharing.domain.models

data class Email(
    val address: String,
    val subject: String,
    val body: String
)