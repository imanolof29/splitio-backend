package com.imanolortiz.splitio.auth.service.impl

import com.imanolortiz.splitio.auth.service.HashService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
final class HashServiceImpl : HashService {

    private val encoder = BCryptPasswordEncoder()

    override fun hashPassword(password: String): String {
        return encoder.encode(password)!!
    }

    override fun compare(hashedPassword: String, password: String): Boolean {
        return encoder.matches(password, hashedPassword)
    }
}
