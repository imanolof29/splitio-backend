package com.imanolortiz.splitio.invitations.service.impl

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.auth.repository.UserRepository
import com.imanolortiz.splitio.invitations.entity.Invitation
import com.imanolortiz.splitio.invitations.entity.InvitationStatus
import com.imanolortiz.splitio.invitations.repository.InvitationRepository
import com.imanolortiz.splitio.invitations.service.InvitationService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@Service
class InvitationServiceImpl(
    private val invitationRepository: InvitationRepository,
    private val userRepository: UserRepository
) : InvitationService {

    override fun create(
        groupId: String,
        authenticatedUser: AuthenticatedUser
    ): Invitation {
        TODO("Not yet implemented")
    }

    override fun accept(token: String, authenticatedUser: AuthenticatedUser) {
        val invitation = invitationRepository.findByToken(token)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Invitation does not exist"
            )

        if (invitation.status != InvitationStatus.PENDING) {
            throw ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Invitation is not pending"
            )
        }

        val now = Instant.now()

        if (invitation.expiresAt?.isBefore(now) == true) {
            invitation.status = InvitationStatus.EXPIRED
            invitationRepository.save(invitation)

            throw ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Invitation has expired"
            )
        }

        val user = userRepository.findById(authenticatedUser.id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found"
                )
            }

        if (!invitation.group.members.contains(user)) {
            invitation.group.members.add(user)
        }

        invitation.status = InvitationStatus.ACCEPTED
        invitation.updatedAt = now

        invitationRepository.save(invitation)
    }

}