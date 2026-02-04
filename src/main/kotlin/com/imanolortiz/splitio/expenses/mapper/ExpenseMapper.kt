package com.imanolortiz.splitio.expenses.mapper

import com.imanolortiz.splitio.expenses.dto.ExpenseDto
import com.imanolortiz.splitio.expenses.entity.Expense

fun Expense.toDto(): ExpenseDto = ExpenseDto(
    id = this.id,
    groupId = this.group.id,
    creatorId = this.creator.id,
    description = this.description,
    amount = this.amount,
    expenseDate = this.expenseDate,
)
