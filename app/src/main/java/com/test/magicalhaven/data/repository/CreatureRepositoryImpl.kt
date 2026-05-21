package com.test.magicalhaven.data.repository

import android.content.Context
import com.test.magicalhaven.R
import com.test.magicalhaven.domain.model.Creature
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
                    // Используем limit = 7, чтобы не разбивать способности внутри кавычек
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

    override fun getAllCreatures(): List<Creature> = creatures

    override fun getCreatureById(id: String): Creature? = creatures.find { it.id == id }

    override fun removeCreatureById(id: String): Boolean {
        val removed = creatures.removeIf { it.id == id }
        if (removed) adoptedCount++
        return removed
    }

    override fun getAvailableCount(): Int = creatures.size

    override fun getAdoptedCount(): Int = adoptedCount

    override fun getMostPopularSpecies(): String? {
        if (creatures.isEmpty()) return null
        return creatures.groupBy { it.species }
            .maxByOrNull { it.value.size }?.key
    }
}
