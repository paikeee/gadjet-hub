package ru.spbstu.gadget_hub.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.spbstu.gadget_hub.model.entities.User
import ru.spbstu.gadget_hub.repositories.UserRepository

@Service
class JwtUserDetailsService(
        private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByLogin(username)
                ?: throw UsernameNotFoundException("User $username not found!")

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.login)
                .password(user.password)
                .build()
    }

    fun getUser(username: String) : User =
            userRepository.findByLogin(username) ?: throw UsernameNotFoundException("User $username not found!")
}