package com.imanolortiz.splitio.groups.repository

import com.imanolortiz.splitio.groups.entity.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Long>