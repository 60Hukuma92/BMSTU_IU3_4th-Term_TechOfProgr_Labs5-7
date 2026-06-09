package com.test.magicalhaven.server.service

import com.test.magicalhaven.server.mapper.CreatureEntityMapper
import com.test.magicalhaven.server.repository.CreatureRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class CreatureService(
    private val creatureRepository: CreatureRepository,
    private val creatureEntityMapper: CreatureEntityMapper
) {
    open fun getAllCreatures(): List<com.test.magicalhaven.server.model.Creature> =
        creatureRepository.findAll()
            .map { creatureEntityMapper.toDomain(it) }

    open fun getAvailableCreatures(): List<com.test.magicalhaven.server.model.Creature> =
        creatureRepository.findByIsAdoptedFalse()
            .map { creatureEntityMapper.toDomain(it) }

    @Transactional
    open fun adoptCreature(id: Long): com.test.magicalhaven.server.model.Creature? {
        val entity = creatureRepository.findById(id).orElse(null)
        if (entity != null && !entity.isAdopted) {
            entity.isAdopted = true
            creatureRepository.save(entity)
            return creatureEntityMapper.toDomain(entity)
        }
        return null
    }
}