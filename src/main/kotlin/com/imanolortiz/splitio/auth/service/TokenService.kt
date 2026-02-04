package com.imanolortiz.splitio.auth.service

import com.imanolortiz.splitio.auth.model.AuthenticatedUser

interface TokenService {
    fun generateAccessToken(user: AuthenticatedUser): String
    fun generateRefreshToken(user: AuthenticatedUser): String
    fun extractUser(token: String): AuthenticatedUser?
    fun isValid(token: String): Boolean
    fun isRefreshToken(token: String): Boolean
}
