package ru.spbstu.gadget_hub.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.spbstu.gadget_hub.model.dto.AuthenticationRequest
import ru.spbstu.gadget_hub.model.dto.AuthenticationResponse
import ru.spbstu.gadget_hub.service.AuthService

@RestController
class AuthController(
        private val authService: AuthService
) {
    @PostMapping("/login")
    fun authenticate(
            @RequestBody authRequest: AuthenticationRequest
    ): AuthenticationResponse =
            authService.authentication(authRequest)
}