package com.imanolortiz.splitio.expenses.dto

import java.math.BigDecimal
import java.time.LocalDate

data class ExpenseDto(
    val id: Long,
    val groupId: Long,
    val creatorId: Long,
    val description: String?,
    val amount: BigDecimal,
    val expenseDate: LocalDate,
)
