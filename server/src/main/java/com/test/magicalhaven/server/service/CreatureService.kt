package com.test.magicalhaven.server.service

import com.test.magicalhaven.server.model.Creature
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class CreatureService(
    private val resourceLoader: ResourceLoader,
    @Value("\${app.data.csv-path}") private val csvPath: String
) : InitializingBean {
    private val creatures = mutableListOf<Creature>()

    override fun afterPropertiesSet() {
        loadData()
    }

    private fun loadData() {
        val resource = resourceLoader.getResource("classpath:\$csvPath")
        if (resource.exists()) {
            resource.inputStream.bufferedReader().useLines { lines ->
                lines.drop(1).forEach { line ->
                    // Simple CSV parsing (considering quotes for abilities)
                    val regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()
                    val parts = line.split(regex)
                    if (parts.size >= 8) {
                        val rawAbilities = parts[6].trim()
                        val cleanAbilities = if (rawAbilities.startsWith("\"") && rawAbilities.endsWith("\"")) {
                            rawAbilities.substring(1, rawAbilities.length - 1)
                        } else {
                            rawAbilities
                        }

                        creatures.add(
                            Creature(
                                id = parts[0].toLong(),
                                name = parts[1],
                                species = parts[2],
                                temperament = parts[3],
                                dailyExpenses = parts[4].toDouble(),
                                adoptionCost = parts[5].toDouble(),
                                magicalAbilities = cleanAbilities.split(",").map { it.trim() },
                                isAdopted = parts[7].toBoolean()
                            )
                        )
                    }
                }
            }
        } else {
            println("Warning: CSV resource not found at classpath:\$csvPath")
        }
    }

    fun getAllCreatures(): List<Creature> = creatures.toList()

    fun getAvailableCreatures(): List<Creature> = creatures.filter { !it.isAdopted }

    fun adoptCreature(id: Long): Creature? {
        val creature = creatures.find { it.id == id && !it.isAdopted }
        if (creature != null) {
            creature.isAdopted = true
        }
        return creature
    }
}
