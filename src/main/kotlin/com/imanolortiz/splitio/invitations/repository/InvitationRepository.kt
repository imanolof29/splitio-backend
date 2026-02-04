package com.imanolortiz.splitio.invitations.repository

import com.imanolortiz.splitio.invitations.entity.Invitation
import org.springframework.data.jpa.repository.JpaRepository

interface InvitationRepository : JpaRepository<Invitation, Long> {
    fun findByToken(token: String): Invitation?
}