package com.imanolortiz.splitio.invitations.controller

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.invitations.dto.CreateInvitationDto
import com.imanolortiz.splitio.invitations.service.InvitationService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("invitations")
@RestController
final class InvitationController(
    private val invitationService: InvitationService
) {

    @PostMapping
    fun createInvitation(
        @RequestBody dto: CreateInvitationDto,
        @AuthenticationPrincipal principal: AuthenticatedUser
    ) {
        invitationService.create(dto, principal)
    }

    @PostMapping("token/{token}/accept")
    fun acceptInvitation(
        @AuthenticationPrincipal principal: AuthenticatedUser,
        @PathVariable("token") token: String,
    ) {
        invitationService.accept(token, principal)
    }

}