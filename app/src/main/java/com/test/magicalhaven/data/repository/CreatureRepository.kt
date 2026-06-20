package com.test.magicalhaven.data.repository

import com.test.magicalhaven.domain.model.Creature

interface CreatureRepository {
    suspend fun getAllCreatures(): List<Creature>
    suspend fun getCreatureById(id: String): Creature?
    suspend fun removeCreatureById(id: String): Boolean
    suspend fun getAvailableCount(): Int
    suspend fun getAdoptedCount(): Int
    suspend fun getMostPopularSpecies(): String?
    suspend fun getMyCreatures(): List<Creature>
    suspend fun getPlayerInfo(): com.test.magicalhaven.domain.model.Player?
    suspend fun login(username: String, password: String): Boolean
}
