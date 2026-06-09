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
        isAdopted = domain.isAdopted
    )
}