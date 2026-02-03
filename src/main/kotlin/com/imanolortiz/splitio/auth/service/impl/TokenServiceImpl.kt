package com.imanolortiz.splitio.auth.service.impl

import com.imanolortiz.splitio.auth.service.TokenService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
final class TokenServiceImpl(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-expiration}") private val accessExpiration: Long,
    @Value("\${jwt.refresh-expiration}") private val refreshExpiration: Long,
) : TokenService {

    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))
    }

    override fun generateAccessToken(email: String): String {
        return buildToken(email, accessExpiration, "access")
    }

    override fun generateRefreshToken(email: String): String {
        return buildToken(email, refreshExpiration, "refresh")
    }

    override fun extractEmail(token: String): String? {
        return try {
            parseClaims(token)?.subject
        } catch (e: Exception) {
            null
        }
    }

    override fun isValid(token: String): Boolean {
        return extractEmail(token) != null
    }

    override fun isRefreshToken(token: String): Boolean {
        return try {
            parseClaims(token)?.get("type", String::class.java) == "refresh"
        } catch (e: Exception) {
            false
        }
    }

    private fun buildToken(email: String, expiration: Long, type: String): String {
        val now = Date()
        return Jwts.builder()
            .subject(email)
            .claim("type", type)
            .issuedAt(now)
            .expiration(Date(now.time + expiration))
            .signWith(key)
            .compact()
    }

    private fun parseClaims(token: String) =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}
