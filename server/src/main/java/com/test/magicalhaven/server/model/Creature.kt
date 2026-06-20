package com.test.magicalhaven.server.model

import java.math.BigDecimal

data class Creature(
    val id: Long = 0,
    val name: String = "",
    val species: String = "",
    val temperament: String = "",
    val dailyExpenses: BigDecimal = BigDecimal.ZERO,
    val adoptionCost: BigDecimal = BigDecimal.ZERO,
    val magicalAbilities: List<String> = emptyList(),
    val isAdopted: Boolean = false
)
