package com.imanolortiz.splitio.auth.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "full_name", nullable = false, length = 150)
    val fullName: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(name = "password_hash")
    val passwordHash: String? = null,

    @Column(name = "profile_image_url")
    val profileImageUrl: String? = null,

    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now(),
)