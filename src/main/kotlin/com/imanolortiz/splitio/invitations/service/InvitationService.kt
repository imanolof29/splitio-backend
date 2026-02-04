package com.imanolortiz.splitio.invitations.service

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.invitations.dto.CreateInvitationDto
import com.imanolortiz.splitio.invitations.entity.Invitation

interface InvitationService {
    fun create(dto: CreateInvitationDto, principal: AuthenticatedUser): Invitation
    fun accept(token: String, principal: AuthenticatedUser)
}