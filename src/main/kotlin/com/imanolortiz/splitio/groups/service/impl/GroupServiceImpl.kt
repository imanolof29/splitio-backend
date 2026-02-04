package com.imanolortiz.splitio.groups.service.impl

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.auth.repository.UserRepository
import com.imanolortiz.splitio.groups.dto.CreateGroupDto
import com.imanolortiz.splitio.groups.dto.UpdateGroupDto
import com.imanolortiz.splitio.groups.entity.Group
import com.imanolortiz.splitio.groups.mapper.toGroup
import com.imanolortiz.splitio.groups.repository.GroupRepository
import com.imanolortiz.splitio.groups.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
final class GroupServiceImpl(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : GroupService {

    override fun findAll(): List<Group> {
        return groupRepository.findAll()
    }

    override fun findById(id: Long): Group? {
        return groupRepository.findById(id).orElse(null)
    }

    override fun save(group: Group): Group {
        return groupRepository.save(group)
    }

    override fun create(dto: CreateGroupDto, principal: AuthenticatedUser): Group {
        val user = userRepository.findById(principal.id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        val group = dto.toGroup()
        group.members.add(user)
        return groupRepository.save(group)
    }

    override fun delete(id: Long) {
        val group = groupRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found") }
        groupRepository.delete(group)
    }

    override fun update(
        id: Long,
        dto: UpdateGroupDto
    ): Group {
        val group = groupRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found") }
        group.name = dto.name
        return groupRepository.save(group)
    }
}
