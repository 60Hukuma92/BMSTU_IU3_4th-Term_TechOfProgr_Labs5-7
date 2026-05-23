package com.test.magicalhaven.server.model

data class Creature(
    val id: Long,
    val name: String,
    val species: String,
    val temperament: String,
    val dailyExpenses: Double,
    val adoptionCost: Double,
    val magicalAbilities: List<String>,
    var isAdopted: Boolean
)
