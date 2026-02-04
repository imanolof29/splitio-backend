package com.imanolortiz.splitio.groups.mapper

import com.imanolortiz.splitio.groups.dto.CreateGroupDto
import com.imanolortiz.splitio.groups.dto.GroupDto
import com.imanolortiz.splitio.groups.entity.Group

fun Group.toDto(): GroupDto = GroupDto(
    id = this.id,
    name = this.name,
)

fun CreateGroupDto.toGroup(): Group = Group(
    name = this.name
)