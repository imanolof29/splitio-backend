package com.imanolortiz.splitio.invitations.service

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.invitations.entity.Invitation

interface InvitationService {
    fun create(groupId: String, authenticatedUser: AuthenticatedUser): Invitation
    fun accept(token: String, authenticatedUser: AuthenticatedUser)
}