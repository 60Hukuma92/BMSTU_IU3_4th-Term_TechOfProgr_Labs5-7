package com.test.magicalhaven.server.dto

import java.math.BigDecimal

data class PlayerDto(
    val id: Long,
    val username: String,
    val balance: BigDecimal,
    val adoptedCreatures: List<CreatureDto>
)
