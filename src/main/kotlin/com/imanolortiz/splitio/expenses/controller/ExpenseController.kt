package com.imanolortiz.splitio.expenses.controller

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.expenses.dto.AddExpenseDto
import com.imanolortiz.splitio.expenses.dto.ExpenseDto
import com.imanolortiz.splitio.expenses.mapper.toDto
import com.imanolortiz.splitio.expenses.service.ExpenseService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("groups/{groupId}/expenses")
@RestController
final class ExpenseController(
    private val expenseService: ExpenseService
) {

    @GetMapping
    fun getAllExpenses(@PathVariable("groupId") groupId: Long): List<ExpenseDto> {
        return expenseService.findAllByGroup(groupId).map { it.toDto() }
    }

    @PostMapping
    fun addExpense(
        @PathVariable("groupId") groupId: Long,
        @RequestBody dto: AddExpenseDto,
        @AuthenticationPrincipal principal: AuthenticatedUser
    ): ExpenseDto {
        return expenseService.create(groupId, dto, principal).toDto()
    }

    @DeleteMapping("{expenseId}")
    fun deleteExpense(
        @PathVariable("groupId") groupId: Long,
        @PathVariable("expenseId") expenseId: Long
    ) {
        expenseService.delete(groupId, expenseId)
    }
}
