package com.test.magicalhaven.data.repository

import com.test.magicalhaven.domain.model.Creature

interface CreatureRepository {
    fun getAllCreatures(): List<Creature>
}
