package com.imanolortiz.splitio.auth.service

interface TokenService {
    fun generateAccessToken(email: String): String
    fun generateRefreshToken(email: String): String
    fun extractEmail(token: String): String?
    fun isValid(token: String): Boolean
    fun isRefreshToken(token: String): Boolean
}