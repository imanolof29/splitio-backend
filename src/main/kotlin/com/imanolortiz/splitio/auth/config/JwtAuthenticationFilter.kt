package com.imanolortiz.splitio.auth.config

import com.imanolortiz.splitio.auth.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenService: TokenService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val header = request.getHeader("Authorization")

        if (header != null && header.startsWith("Bearer ")) {
            val token = header.substring(7)

            if (!tokenService.isRefreshToken(token)) {
                val user = tokenService.extractUser(token)
                if (user != null) {
                    val auth = UsernamePasswordAuthenticationToken(user, null, emptyList())
                    SecurityContextHolder.getContext().authentication = auth
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
