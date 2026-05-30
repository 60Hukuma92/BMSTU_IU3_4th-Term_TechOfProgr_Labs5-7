package com.test.magicalhaven.server.repository.entity

data class CreatureEntity(
    val id: Long,
    var name: String,
    var species: String,
    var temperament: String,
    var dailyExpenses: Double,
    var adoptionCost: Double,
    var magicalAbilities: List<String>,
    var isAdopted: Boolean
)

