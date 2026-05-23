package com.test.magicalhaven.data.repository

import com.test.magicalhaven.data.remote.CreatureApiService
import com.test.magicalhaven.domain.model.Creature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteCreatureRepositoryImpl(
    private val apiService: CreatureApiService
) : CreatureRepository {

    override suspend fun getAllCreatures(): List<Creature> = withContext(Dispatchers.IO) {
        try {
            apiService.getAvailableCreatures()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getCreatureById(id: String): Creature? = withContext(Dispatchers.IO) {
        getAllCreatures().find { it.id == id }
    }

    override suspend fun removeCreatureById(id: String): Boolean = withContext(Dispatchers.IO) {
        try {
            apiService.adoptCreature(id)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getAvailableCount(): Int = getAllCreatures().size

    override suspend fun getAdoptedCount(): Int = 0 

    override suspend fun getMostPopularSpecies(): String? = withContext(Dispatchers.IO) {
        val all = getAllCreatures()
        if (all.isEmpty()) return@withContext null
        all.groupBy { it.species }
            .maxByOrNull { it.value.size }?.key
    }
}
