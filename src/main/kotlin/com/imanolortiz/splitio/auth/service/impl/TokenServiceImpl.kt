package com.imanolortiz.splitio.auth.service.impl

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.auth.service.TokenService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
final class TokenServiceImpl(
    @Value("\${JWT_SECRET}") private val secret: String,
    @Value("\${jwt.access-expiration}") private val accessExpiration: Long,
    @Value("\${jwt.refresh-expiration}") private val refreshExpiration: Long,
) : TokenService {

    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))
    }

    override fun generateAccessToken(user: AuthenticatedUser): String {
        return buildToken(user, accessExpiration, "access")
    }

    override fun generateRefreshToken(user: AuthenticatedUser): String {
        return buildToken(user, refreshExpiration, "refresh")
    }

    override fun extractUser(token: String): AuthenticatedUser? {
        return try {
            val claims = parseClaims(token) ?: return null
            val id = claims.subject?.toLongOrNull() ?: return null
            val email = claims.get("email", String::class.java) ?: return null
            AuthenticatedUser(id = id, email = email)
        } catch (e: Exception) {
            null
        }
    }

    override fun isValid(token: String): Boolean {
        return extractUser(token) != null
    }

    override fun isRefreshToken(token: String): Boolean {
        return try {
            parseClaims(token)?.get("type", String::class.java) == "refresh"
        } catch (e: Exception) {
            false
        }
    }

    private fun buildToken(user: AuthenticatedUser, expiration: Long, type: String): String {
        val now = Date()
        return Jwts.builder()
            .subject(user.id.toString())
            .claim("email", user.email)
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
