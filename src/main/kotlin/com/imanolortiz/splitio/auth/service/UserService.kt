package com.imanolortiz.splitio.auth.service

import com.imanolortiz.splitio.auth.entity.User

interface UserService {
    fun findByEmail(email: String): User?
    fun save(user: User): User
}