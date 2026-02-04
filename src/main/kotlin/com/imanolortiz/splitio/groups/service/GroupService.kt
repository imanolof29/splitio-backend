package com.imanolortiz.splitio.groups.service

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.groups.dto.CreateGroupDto
import com.imanolortiz.splitio.groups.entity.Group

interface GroupService {
    fun findAll(): List<Group>
    fun findById(id: Long): Group?
    fun save(group: Group): Group
    fun create(dto: CreateGroupDto, principal: AuthenticatedUser): Group
}