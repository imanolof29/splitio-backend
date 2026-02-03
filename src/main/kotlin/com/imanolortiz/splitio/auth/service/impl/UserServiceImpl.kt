package com.imanolortiz.splitio.auth.service.impl

import com.imanolortiz.splitio.auth.entity.User
import com.imanolortiz.splitio.auth.repository.UserRepository
import com.imanolortiz.splitio.auth.service.UserService
import org.springframework.stereotype.Service

@Service
final class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override fun save(user: User): User {
        return userRepository.save(user)
    }
}