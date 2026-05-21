package com.test.magicalhaven.data.repository

import com.test.magicalhaven.domain.model.Creature

interface CreatureRepository {
    fun getAllCreatures(): List<Creature>

    fun getCreatureById(id: String): Creature?

    fun removeCreatureById(id: String): Boolean


    fun getAvailableCount(): Int

    fun getAdoptedCount(): Int

    fun getMostPopularSpecies(): String?
}
