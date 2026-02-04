package com.imanolortiz.splitio.expenses.service.impl

import com.imanolortiz.splitio.auth.model.AuthenticatedUser
import com.imanolortiz.splitio.auth.repository.UserRepository
import com.imanolortiz.splitio.expenses.dto.AddExpenseDto
import com.imanolortiz.splitio.expenses.entity.Expense
import com.imanolortiz.splitio.expenses.repository.ExpenseRepository
import com.imanolortiz.splitio.expenses.service.ExpenseService
import com.imanolortiz.splitio.groups.repository.GroupRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
final class ExpenseServiceImpl(
    private val expenseRepository: ExpenseRepository,
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : ExpenseService {

    override fun findAllByGroup(groupId: Long): List<Expense> {
        groupRepository.findById(groupId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found") }
        return expenseRepository.findByGroupId(groupId)
    }

    override fun create(groupId: Long, dto: AddExpenseDto, principal: AuthenticatedUser): Expense {
        val group = groupRepository.findById(groupId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found") }
        val user = userRepository.findById(principal.id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }

        val expense = Expense(
            group = group,
            creator = user,
            description = dto.description,
            amount = dto.amount,
            expenseDate = dto.expenseDate,
        )

        return expenseRepository.save(expense)
    }

    override fun delete(groupId: Long, expenseId: Long) {
        val expense = expenseRepository.findById(expenseId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found") }

        if (expense.group.id != groupId) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found")
        }

        expenseRepository.delete(expense)
    }
}
