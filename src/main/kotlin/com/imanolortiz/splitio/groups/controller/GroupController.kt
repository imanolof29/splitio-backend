package com.imanolortiz.splitio.groups.controller

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.groups.dto.CreateGroupDto
import com.imanolortiz.splitio.groups.dto.GroupDto
import com.imanolortiz.splitio.groups.mapper.toDto
import com.imanolortiz.splitio.groups.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RequestMapping("groups")
@RestController
final class GroupController(
    private val groupService: GroupService
) {

    @GetMapping()
    fun getAllGroups(): List<GroupDto> {
        return groupService.findAll().map { it.toDto() }
    }

    @GetMapping("{id}")
    fun getGroup(@PathVariable("id") id: Long): GroupDto =
        groupService.findById(id)?.toDto()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found")

    @PostMapping
    fun createGroup(
        @RequestBody dto: CreateGroupDto,
        @AuthenticationPrincipal principal: AuthenticatedUser
    ): GroupDto {
        return groupService.create(dto, principal).toDto()
    }

}