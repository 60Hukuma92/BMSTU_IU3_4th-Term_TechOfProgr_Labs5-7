package com.test.magicalhaven.server.mapper

import com.test.magicalhaven.server.model.Creature
import com.test.magicalhaven.server.repository.entity.CreatureEntity
import org.springframework.stereotype.Component

@Component
class CreatureEntityMapper {

    fun toDomain(entity: CreatureEntity): Creature = Creature(
        id = entity.id,
        name = entity.name,
        species = entity.species,
        temperament = entity.temperament,
        dailyExpenses = entity.dailyExpenses,
        adoptionCost = entity.adoptionCost,
        magicalAbilities = entity.magicalAbilities,
        isAdopted = entity.isAdopted
    )

    fun toEntity(domain: Creature): CreatureEntity = CreatureEntity(
        id = domain.id,
        name = domain.name,
        species = domain.species,
        temperament = domain.temperament,
        dailyExpenses = domain.dailyExpenses,
        adoptionCost = domain.adoptionCost,
        magicalAbilities = domain.magicalAbilities,
        isAdopted = domain.isAdopted
    )
}