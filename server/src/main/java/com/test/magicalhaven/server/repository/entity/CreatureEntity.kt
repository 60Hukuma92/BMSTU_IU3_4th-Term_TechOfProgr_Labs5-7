package com.test.magicalhaven.server.repository.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "creatures")
class CreatureEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var species: String = "",

    @Column(nullable = false)
    var temperament: String = "",

    @Column(name = "daily_expenses", nullable = false, precision = 10, scale = 2)
    var dailyExpenses: BigDecimal = BigDecimal.ZERO,

    @Column(name = "adoption_cost", nullable = false, precision = 10, scale = 2)
    var adoptionCost: BigDecimal = BigDecimal.ZERO,

    @Column(name = "magical_abilities", columnDefinition = "text[]")
    var magicalAbilities: List<String> = emptyList(),

    @Column(name = "is_adopted", nullable = false)
    var isAdopted: Boolean = false
)