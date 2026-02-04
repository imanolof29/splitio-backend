package com.imanolortiz.splitio.expenses.entity

import com.imanolortiz.splitio.auth.entity.User
import com.imanolortiz.splitio.groups.entity.Group
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

@Entity
@Table(name = "expenses")
class Expense(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "group_id")
    val group: Group,

    @ManyToOne
    @JoinColumn(name = "creator_id")
    val creator: User,

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal,

    @Column(name = "expense_date", nullable = false)
    val expenseDate: LocalDate,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @ManyToMany
    @JoinTable(
        name = "expense_participants",
        joinColumns = [JoinColumn(name = "expense_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val participants: MutableSet<User> = mutableSetOf()
)
