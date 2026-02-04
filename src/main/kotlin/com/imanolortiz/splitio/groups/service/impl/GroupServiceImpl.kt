package com.imanolortiz.splitio.groups.service.impl

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.auth.repository.UserRepository
import com.imanolortiz.splitio.groups.dto.CreateGroupDto
import com.imanolortiz.splitio.groups.entity.Group
import com.imanolortiz.splitio.groups.mapper.toGroup
import com.imanolortiz.splitio.groups.repository.GroupRepository
import com.imanolortiz.splitio.groups.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class GroupServiceImpl(
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
}
