package com.imanolortiz.splitio.auth.dto.out

data class TokenResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
