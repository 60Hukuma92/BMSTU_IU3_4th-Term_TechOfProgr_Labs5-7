package com.test.magicalhaven.domain.model

data class Player(
    val id: Long,
    val username: String,
    val balance: Double,
    val adoptedCreatures: List<Creature>
)
