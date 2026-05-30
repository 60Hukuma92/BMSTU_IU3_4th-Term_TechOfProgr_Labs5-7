package com.test.magicalhaven.server.dto

data class CreatureDto(
    val id: Long,
    val name: String,
    val species: String,
    val temperament: String,
    val dailyExpenses: Double,
    val adoptionCost: Double,
    val magicalAbilities: List<String>,
    val adopted: Boolean
)

