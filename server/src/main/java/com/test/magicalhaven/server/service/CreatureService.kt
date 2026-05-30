package com.test.magicalhaven.server.service

import com.test.magicalhaven.server.mapper.CreatureEntityMapper
import com.test.magicalhaven.server.model.Creature
import com.test.magicalhaven.server.repository.CreatureRepository
import org.springframework.stereotype.Service

@Service
class CreatureService(
    private val creatureRepository: CreatureRepository,
    private val creatureEntityMapper: CreatureEntityMapper
) {
    fun getAllCreatures(): List<Creature> = creatureRepository.getAll()
        .map(creatureEntityMapper::toDomain)

    fun getAvailableCreatures(): List<Creature> = creatureRepository.getAll()
        .filter { !it.isAdopted }
        .map(creatureEntityMapper::toDomain)

    fun adoptCreature(id: Long): Creature? {
        val entity = creatureRepository.findById(id)
        if (entity != null && !entity.isAdopted) {
            entity.isAdopted = true
            creatureRepository.update(entity)
            return creatureEntityMapper.toDomain(entity)
        }
        return null
    }
}
