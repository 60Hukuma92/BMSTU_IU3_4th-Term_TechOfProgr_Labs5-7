package com.test.magicalhaven.server.mapper

import com.test.magicalhaven.server.dto.CreatureDto
import com.test.magicalhaven.server.model.Creature
import org.springframework.stereotype.Component

@Component
class CreatureDtoMapper {
    fun toDto(domain: Creature): CreatureDto = CreatureDto(
        id = domain.id,
        name = domain.name,
        species = domain.species,
        temperament = domain.temperament,
        dailyExpenses = domain.dailyExpenses,
        adoptionCost = domain.adoptionCost,
        magicalAbilities = domain.magicalAbilities,
        adopted = domain.isAdopted
    )

    fun toDomain(dto: CreatureDto): Creature = Creature(
        id = dto.id,
        name = dto.name,
        species = dto.species,
        temperament = dto.temperament,
        dailyExpenses = dto.dailyExpenses,
        adoptionCost = dto.adoptionCost,
        magicalAbilities = dto.magicalAbilities,
        isAdopted = dto.adopted
    )
}

