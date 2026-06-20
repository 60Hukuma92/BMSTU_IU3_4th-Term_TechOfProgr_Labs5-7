package com.test.magicalhaven.server.service

import com.test.magicalhaven.server.mapper.CreatureEntityMapper
import com.test.magicalhaven.server.model.Creature
import com.test.magicalhaven.server.repository.CreatureRepository
import com.test.magicalhaven.server.repository.PlayerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class CreatureService(
    private val creatureRepository: CreatureRepository,
    private val playerRepository: PlayerRepository,
    private val creatureEntityMapper: CreatureEntityMapper
) {
    open fun getAllCreatures(): List<Creature> =
        creatureRepository.findAll()
            .map { creatureEntityMapper.toDomain(it) }

    open fun getAvailableCreatures(): List<Creature> =
        creatureRepository.findByIsAdoptedFalse()
            .map { creatureEntityMapper.toDomain(it) }

    open fun getById(id: Long): Creature? =
        creatureRepository.findById(id)
            .map { creatureEntityMapper.toDomain(it) }
            .orElse(null)

    @Transactional
    open fun create(creature: Creature): Creature {
        val entity = creatureEntityMapper.toEntity(creature)
        return creatureEntityMapper.toDomain(creatureRepository.save(entity))
    }

    @Transactional
    open fun update(id: Long, creature: Creature): Creature? {
        if (!creatureRepository.existsById(id)) return null
        val entity = creatureEntityMapper.toEntity(creature)
        entity.id = id
        return creatureEntityMapper.toDomain(creatureRepository.save(entity))
    }

    @Transactional
    open fun delete(id: Long): Boolean {
        return if (creatureRepository.existsById(id)) {
            creatureRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    @Transactional
    open fun adoptCreature(id: Long, username: String): Creature? {
        val entity = creatureRepository.findById(id).orElse(null)
        val player = playerRepository.findByUsername(username).orElse(null)
        
        if (entity == null) {
            println("ADOPT FAIL: Creature with ID $id not found")
            return null
        }
        if (entity.isAdopted) {
            println("ADOPT FAIL: Creature ${entity.name} is already adopted")
            return null
        }
        if (player == null) {
            println("ADOPT FAIL: Player $username not found")
            return null
        }

        if (player.balance >= entity.adoptionCost) {
            player.balance = player.balance.subtract(entity.adoptionCost)
            entity.isAdopted = true
            entity.owner = player
            
            playerRepository.save(player)
            creatureRepository.save(entity)
            println("ADOPT SUCCESS: ${player.username} adopted ${entity.name} for ${entity.adoptionCost}")
            return creatureEntityMapper.toDomain(entity)
        } else {
            println("ADOPT FAIL: Player ${player.username} has ${player.balance}, but needs ${entity.adoptionCost}")
        }
        return null
    }
}
