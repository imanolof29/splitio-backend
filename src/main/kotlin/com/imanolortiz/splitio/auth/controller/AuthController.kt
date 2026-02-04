package com.imanolortiz.splitio.auth.controller

import com.imanolortiz.splitio.auth.dto.`in`.RefreshTokenDto
import com.imanolortiz.splitio.auth.dto.`in`.SignInDto
import com.imanolortiz.splitio.auth.dto.`in`.SignUpDto
import com.imanolortiz.splitio.auth.dto.out.TokenResponseDto
import com.imanolortiz.splitio.auth.mapper.toUser
import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.auth.service.HashService
import com.imanolortiz.splitio.auth.service.TokenService
import com.imanolortiz.splitio.auth.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RequestMapping("auth")
@RestController
class AuthController(
    private val userService: UserService,
    private val hashService: HashService,
    private val tokenService: TokenService,
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody dto: SignUpDto): TokenResponseDto {
        if (userService.findByEmail(dto.email) != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email already in use")
        }

        val user = userService.save(dto.toUser(hashService.hashPassword(dto.password)))

        return generateTokens(AuthenticatedUser(id = user.id, email = user.email))
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody dto: SignInDto): TokenResponseDto {
        val user = userService.findByEmail(dto.email)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")

        if (user.passwordHash == null || !hashService.compare(user.passwordHash, dto.password)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
        }

        return generateTokens(AuthenticatedUser(id = user.id, email = user.email))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody dto: RefreshTokenDto): TokenResponseDto {
        if (!tokenService.isRefreshToken(dto.refreshToken)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token")
        }

        val user = tokenService.extractUser(dto.refreshToken)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token")

        return generateTokens(user)
    }

    private fun generateTokens(user: AuthenticatedUser) = TokenResponseDto(
        accessToken = tokenService.generateAccessToken(user),
        refreshToken = tokenService.generateRefreshToken(user),
    )
}
