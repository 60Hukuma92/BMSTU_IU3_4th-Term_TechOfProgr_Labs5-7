package com.test.magicalhaven.data.repository

import android.content.Context
import com.test.magicalhaven.R
import com.test.magicalhaven.domain.model.Creature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class CreatureRepositoryImpl(
    private val context: Context
) : CreatureRepository {

    private val creatures = mutableListOf<Creature>()
    private var adoptedCount = 0

    init {
        loadCreaturesFromCsv()
    }

    private fun loadCreaturesFromCsv() {
        try {
            val inputStream = context.resources.openRawResource(R.raw.creatures)
            val reader = BufferedReader(InputStreamReader(inputStream))

            reader.useLines { lines ->
                lines.drop(1).forEach { line ->
                    val fields = line.split(",", limit = 7)
                    if (fields.size == 7) {
                        val rawAbilities = fields[6].trim()
                        val cleanAbilities = if (rawAbilities.startsWith("\"") && rawAbilities.endsWith("\"")) {
                            rawAbilities.substring(1, rawAbilities.length - 1)
                        } else {
                            rawAbilities
                        }
                        
                        creatures.add(
                            Creature(
                                id = fields[0],
                                name = fields[1],
                                species = fields[2],
                                temperament = fields[3],
                                dailyExpenses = fields[4].toDouble(),
                                adoptionCost = fields[5].toDouble(),
                                magicalAbilities = cleanAbilities.split(",").map { it.trim() }
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAllCreatures(): List<Creature> = withContext(Dispatchers.IO) {
        creatures
    }

    override suspend fun getCreatureById(id: String): Creature? = withContext(Dispatchers.IO) {
        creatures.find { it.id == id }
    }

    override suspend fun removeCreatureById(id: String): Boolean = withContext(Dispatchers.IO) {
        val removed = creatures.removeIf { it.id == id }
        if (removed) adoptedCount++
        removed
    }

    override suspend fun getAvailableCount(): Int = withContext(Dispatchers.IO) {
        creatures.size
    }

    override suspend fun getAdoptedCount(): Int = withContext(Dispatchers.IO) {
        adoptedCount
    }

    override suspend fun getMostPopularSpecies(): String? = withContext(Dispatchers.IO) {
        if (creatures.isEmpty()) return@withContext null
        creatures.groupBy { it.species }
            .maxByOrNull { it.value.size }?.key
    }

    override suspend fun getMyCreatures(): List<Creature> = emptyList()

    override suspend fun getPlayerInfo(): com.test.magicalhaven.domain.model.Player? = null

    override suspend fun login(username: String, password: String): Boolean = true
}
