package com.imanolortiz.splitio.invitations.service.impl

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.auth.repository.UserRepository
import com.imanolortiz.splitio.groups.repository.GroupRepository
import com.imanolortiz.splitio.invitations.dto.CreateInvitationDto
import com.imanolortiz.splitio.invitations.entity.Invitation
import com.imanolortiz.splitio.invitations.entity.InvitationStatus
import com.imanolortiz.splitio.invitations.repository.InvitationRepository
import com.imanolortiz.splitio.invitations.service.InvitationService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.util.UUID

@Service
class InvitationServiceImpl(
    private val invitationRepository: InvitationRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository
) : InvitationService {

    override fun create(
        dto: CreateInvitationDto,
        principal: AuthenticatedUser
    ): Invitation {
        val group = groupRepository.findById(dto.groupId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found") }
        val inviter = userRepository.findById(principal.id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }

        val invitation = Invitation(
            group = group,
            inviter = inviter,
            token = UUID.randomUUID().toString(),
        )

        return invitationRepository.save(invitation)
    }

    override fun accept(token: String, principal: AuthenticatedUser) {
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

        val user = userRepository.findById(principal.id)
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