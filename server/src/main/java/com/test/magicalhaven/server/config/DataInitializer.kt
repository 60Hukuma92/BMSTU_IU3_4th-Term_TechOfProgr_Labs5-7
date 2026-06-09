package com.test.magicalhaven.server.config

import com.test.magicalhaven.server.repository.CreatureRepository
import com.test.magicalhaven.server.repository.entity.CreatureEntity
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component
class DataInitializer(
    private val creatureRepository: CreatureRepository,
    private val resourceLoader: ResourceLoader,
    @Value("\${app.data.csv-path:creatures.csv}") private val csvPath: String
) : InitializingBean {

    @Transactional
    override fun afterPropertiesSet() {
        if (creatureRepository.count() == 0L) {
            loadDataFromCsv()
        }
    }

    private fun loadDataFromCsv() {
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

                            creatureRepository.save(
                                CreatureEntity(
                                    id = parts[0].trim().toLong(),
                                    name = parts[1].trim(),
                                    species = parts[2].trim(),
                                    temperament = parts[3].trim(),
                                    dailyExpenses = BigDecimal(parts[4].trim()),
                                    adoptionCost = BigDecimal(parts[5].trim()),
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
            println("Successfully loaded creatures into PostgreSQL!")
        }
    }
}