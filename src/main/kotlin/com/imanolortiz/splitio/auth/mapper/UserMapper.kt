package com.imanolortiz.splitio.auth.mapper

import com.imanolortiz.splitio.auth.dto.`in`.SignUpDto
import com.imanolortiz.splitio.auth.entity.User

fun SignUpDto.toUser(passwordHash: String): User = User(
    fullName = fullName,
    email = email,
    passwordHash = passwordHash,
)