package com.test.magicalhaven.server.mapper

import com.test.magicalhaven.server.model.Player
import com.test.magicalhaven.server.repository.entity.PlayerEntity
import org.springframework.stereotype.Component

@Component
class PlayerMapper(private val creatureMapper: CreatureEntityMapper) {

    fun toDomain(entity: PlayerEntity): Player = Player(
        id = entity.id,
        username = entity.username,
        balance = entity.balance,
        adoptedCreatures = entity.adoptedCreatures.map { creatureMapper.toDomain(it) }
    )

    fun toEntity(domain: Player): PlayerEntity = PlayerEntity(
        id = domain.id,
        username = domain.username,
        balance = domain.balance
    )
}
