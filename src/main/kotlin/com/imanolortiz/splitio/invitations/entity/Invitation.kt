package com.imanolortiz.splitio.invitations.entity

import com.imanolortiz.splitio.auth.entity.User
import com.imanolortiz.splitio.groups.entity.Group
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "invitations")
class Invitation(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: Group,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_id", nullable = false)
    val inviter: User,

    @Column(nullable = false, unique = true)
    val token: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: InvitationStatus = InvitationStatus.PENDING,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Column(name = "expires_at")
    val expiresAt: Instant? = null
)
