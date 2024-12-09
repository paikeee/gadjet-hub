package ru.spbstu.gadget_hub.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Service
class TokenService(
        @Value("\${jwt.secret}") private val secret: String = ""
) {
    private val signingKey: SecretKeySpec
        get() {
            val keyBytes: ByteArray = Base64.getDecoder().decode(secret)
            return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
        }

    fun generateToken(subject: String, expiration: Date, additionalClaims: Map<String, Any> = emptyMap()): String {
        val mutableClaims = additionalClaims.toMutableMap()
        return Jwts.builder()
                .claims(mutableClaims)
                .subject(subject)
                .signWith(signingKey)
                .issuedAt(Date(System.currentTimeMillis()))
                .expiration(expiration)
                .compact()
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .payload
    }
}