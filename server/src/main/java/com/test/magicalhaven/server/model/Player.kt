package com.test.magicalhaven.server.model

import java.math.BigDecimal

data class Player(
    val id: Long = 0,
    val username: String = "",
    val balance: BigDecimal = BigDecimal.ZERO,
    val adoptedCreatures: List<Creature> = emptyList()
)
