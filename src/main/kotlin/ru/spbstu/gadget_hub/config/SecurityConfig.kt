package ru.spbstu.gadget_hub.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import ru.spbstu.gadget_hub.repositories.UserRepository
import ru.spbstu.gadget_hub.service.JwtUserDetailsService


@Configuration
class SecurityConfig {
    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService =
            JwtUserDetailsService(userRepository)

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
            config.authenticationManager

    @Bean
    fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider =
            DaoAuthenticationProvider()
                    .also {
                        it.setUserDetailsService(userDetailsService(userRepository))
                        it.setPasswordEncoder(encoder())
                    }

    @Bean
    fun securityFilterChain(
            http: HttpSecurity,
            jwtAuthenticationFilter: JwtAuthorizationFilter,
            authenticationProvider: AuthenticationProvider
    ): DefaultSecurityFilterChain {
        http
                .csrf { it.disable() }
                .cors { corsFilter() }
                .authorizeHttpRequests {
                    it
                            .requestMatchers("/login", "/goods/hit", "/goods/new")
                            .permitAll()
                            .anyRequest()
                            .fullyAuthenticated()
                }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = listOf("http://localhost:5173") // Разрешённые источники
        config.allowedHeaders = listOf("*") // Разрешённые заголовки
        config.setAllowedMethods(listOf("GET", "POST", "PUT", "DELETE")) // Разрешённые методы
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}