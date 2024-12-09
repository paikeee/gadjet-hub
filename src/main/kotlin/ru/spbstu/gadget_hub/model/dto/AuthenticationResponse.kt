package ru.spbstu.gadget_hub.model.dto

data class AuthenticationResponse(
        val login: String,
        val token: String,
)
