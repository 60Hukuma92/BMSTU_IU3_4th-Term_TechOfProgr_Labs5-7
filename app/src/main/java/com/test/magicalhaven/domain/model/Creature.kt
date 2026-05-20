package com.test.magicalhaven.domain.model

data class Creature(
    val id: String,
    val name: String,
    val species: String,
    val temperament: String,
    val dailyExpenses: Double,
    val adoptionCost: Double,
    val magicalAbilities: List<String>
){
    override fun toString(): String =
        "[$id] $name ($species) - $temperament. Daily: $dailyExpenses. Cost: $adoptionCost. Skills: ${magicalAbilities.joinToString()}"
}
