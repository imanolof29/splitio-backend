package com.imanolortiz.splitio.expenses.service

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.expenses.dto.AddExpenseDto
import com.imanolortiz.splitio.expenses.entity.Expense

interface ExpenseService {
    fun findAllByGroup(groupId: Long): List<Expense>
    fun create(groupId: Long, dto: AddExpenseDto, principal: AuthenticatedUser): Expense
    fun delete(groupId: Long, expenseId: Long)
}
