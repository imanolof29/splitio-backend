package com.imanolortiz.splitio.expenses.repository

import com.imanolortiz.splitio.expenses.entity.Expense
import org.springframework.data.jpa.repository.JpaRepository

interface ExpenseRepository : JpaRepository<Expense, Long> {
    fun findByGroupId(groupId: Long): List<Expense>
}
