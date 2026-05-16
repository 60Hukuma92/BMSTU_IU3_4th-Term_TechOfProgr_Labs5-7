package com.test.magicalhaven.domain.model

data class Creature(
    val id: String,
    val name: String,
    val species: String,
    val temperament: String,
    val dailyExpenses: Double,
    val adoptionCost: Double,
    val magicalAbilities: List<String>
)
