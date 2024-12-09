package ru.spbstu.gadget_hub.model.dto

data class AuthenticationRequest(
        val login: String,
        val password: String,
)
