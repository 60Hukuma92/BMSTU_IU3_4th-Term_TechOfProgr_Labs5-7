package com.test.magicalhaven.data.repository

import com.test.magicalhaven.data.remote.AuthApiService
import com.test.magicalhaven.data.remote.AuthInterceptor
import com.test.magicalhaven.data.remote.CreatureApiService
import com.test.magicalhaven.data.remote.PlayerApiService
import com.test.magicalhaven.domain.model.Creature
import com.test.magicalhaven.domain.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteCreatureRepositoryImpl(
    private val apiService: CreatureApiService,
    private val playerApiService: PlayerApiService,
    private val authApiService: AuthApiService,
    private val authInterceptor: AuthInterceptor
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

    override suspend fun getMyCreatures(): List<Creature> = withContext(Dispatchers.IO) {
        try {
            playerApiService.getMyInfo().adoptedCreatures
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getPlayerInfo(): Player? = withContext(Dispatchers.IO) {
        try {
            playerApiService.getMyInfo()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun login(username: String, password: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = authApiService.login(mapOf("username" to username, "password" to password))
            val token = response["token"]
            if (token != null) {
                authInterceptor.token = token
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }
}
