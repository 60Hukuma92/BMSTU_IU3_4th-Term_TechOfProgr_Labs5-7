package com.test.magicalhaven.server.model

import java.math.BigDecimal

data class Creature(
    val id: Long,
    val name: String,
    val species: String,
    val temperament: String,
    val dailyExpenses: BigDecimal,
    val adoptionCost: BigDecimal,
    val magicalAbilities: List<String>,
    val isAdopted: Boolean
)