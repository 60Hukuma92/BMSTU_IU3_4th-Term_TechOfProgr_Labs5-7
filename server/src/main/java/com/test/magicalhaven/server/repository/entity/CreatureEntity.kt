package com.test.magicalhaven.server.repository.entity

import jakarta.persistence.*
import java.sql.Array

@Entity
@Table(name = "creatures")
data class CreatureEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var species: String,

    @Column(nullable = false)
    var temperament: String,

    @Column(name = "daily_expenses", nullable = false)
    var dailyExpenses: Double,

    @Column(name = "adoption_cost", nullable = false)
    var adoptionCost: Double,

    @Column(name = "magical_abilities", columnDefinition = "text[]")
    var magicalAbilities: List<String>,

    @Column(name = "is_adopted", nullable = false)
    var isAdopted: Boolean = false
)