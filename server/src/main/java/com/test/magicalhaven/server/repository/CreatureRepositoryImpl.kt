package com.test.magicalhaven.server.repository

import com.test.magicalhaven.server.repository.entity.CreatureEntity
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Repository

@Repository
class CreatureRepositoryImpl(
    private val resourceLoader: ResourceLoader,
    @param:Value("\${app.data.csv-path}") private val csvPath: String
) : CreatureRepository, InitializingBean {

    private val creatures = mutableListOf<CreatureEntity>()

    override fun afterPropertiesSet() {
        loadData()
    }

    override fun getAll(): List<CreatureEntity> = creatures.toList()

    override fun findById(id: Long): CreatureEntity? = creatures.firstOrNull { it.id == id }

    override fun update(creature: CreatureEntity): CreatureEntity {
        val index = creatures.indexOfFirst { it.id == creature.id }
        if (index >= 0) {
            creatures[index] = creature
        }
        return creature
    }

    private fun loadData() {
        val fullPath = if (csvPath.startsWith("classpath:")) csvPath else "classpath:$csvPath"
        val resource = resourceLoader.getResource(fullPath)

        if (resource.exists()) {
            resource.inputStream.bufferedReader().useLines { lines ->
                lines.drop(1).forEach { line ->
                    if (line.isBlank()) return@forEach
                    val regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()
                    val parts = line.split(regex)
                    if (parts.size >= 8) {
                        try {
                            val rawAbilities = parts[6].trim()
                            val cleanAbilities = if (rawAbilities.startsWith("\"") && rawAbilities.endsWith("\"")) {
                                rawAbilities.substring(1, rawAbilities.length - 1)
                            } else {
                                rawAbilities
                            }

                            creatures.add(
                                CreatureEntity(
                                    id = parts[0].trim().toLong(),
                                    name = parts[1].trim(),
                                    species = parts[2].trim(),
                                    temperament = parts[3].trim(),
                                    dailyExpenses = parts[4].trim().toDouble(),
                                    adoptionCost = parts[5].trim().toDouble(),
                                    magicalAbilities = cleanAbilities.split(",").map { it.trim() },
                                    isAdopted = parts[7].trim().toBoolean()
                                )
                            )
                        } catch (e: Exception) {
                            println("Error parsing CSV line: $line. Error: ${e.message}")
                        }
                    }
                }
            }
            println("Successfully loaded ${creatures.size} creatures from $fullPath")
        } else {
            println("CRITICAL ERROR: CSV resource not found at $fullPath")
        }
    }
}
