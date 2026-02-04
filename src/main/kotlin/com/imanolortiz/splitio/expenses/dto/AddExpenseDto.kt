package com.imanolortiz.splitio.expenses.dto

import java.math.BigDecimal
import java.time.LocalDate

data class AddExpenseDto(
    val amount: BigDecimal,
    val description: String?,
    val expenseDate: LocalDate,
)
