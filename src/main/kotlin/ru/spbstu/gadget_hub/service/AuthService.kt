package ru.spbstu.gadget_hub.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.spbstu.gadget_hub.model.dto.AuthenticationRequest
import ru.spbstu.gadget_hub.model.dto.AuthenticationResponse
import java.util.*

@Service
class AuthService(
        private val authManager: AuthenticationManager,
        private val userDetailsService: UserDetailsService,
        private val tokenService: TokenService,
        @Value("\${jwt.accessTokenExpiration}") private val accessTokenExpiration: Long = 0,
) {
    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        authenticationRequest.login,
                        authenticationRequest.password
                )
        )

        val user = userDetailsService.loadUserByUsername(authenticationRequest.login)

        val accessToken = createAccessToken(user)

        return AuthenticationResponse(
                 login = authenticationRequest.login,
                 token = accessToken,
        )
    }

    fun isUserAuthenticated(): Boolean {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        return authentication != null && authentication.isAuthenticated && authentication.name != "anonymousUser"
    }

    private fun createAccessToken(user: UserDetails): String {
        return tokenService.generateToken(
                subject = user.username,
                expiration = Date(System.currentTimeMillis() + accessTokenExpiration)
        )
    }
}