package com.imanolortiz.splitio.auth.service

interface HashService {
    fun hashPassword(password: String): String
    fun compare(hashedPassword: String, password: String): Boolean
}