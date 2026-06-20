package com.test.magicalhaven.server.config

import com.test.magicalhaven.server.repository.CreatureRepository
import com.test.magicalhaven.server.repository.entity.CreatureEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DataInitializer(
    private val creatureRepository: CreatureRepository,
    private val resourceLoader: ResourceLoader,
    @Value("\${app.data.csv-path:creatures.csv}") private val csvPath: String
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (creatureRepository.count() == 0L) {
            loadFromCsv()
        }
    }

    private fun loadFromCsv() {
        val resource = resourceLoader.getResource("classpath:$csvPath")
        if (!resource.exists()) return

        resource.inputStream.bufferedReader().useLines { lines ->
            lines.drop(1).forEach { line ->
                if (line.isBlank()) return@forEach
                val regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()
                val parts = line.split(regex)
                if (parts.size >= 7) {
                    val rawAbilities = parts[6].trim()
                    val cleanAbilities = if (rawAbilities.startsWith("\"") && rawAbilities.endsWith("\"")) {
                        rawAbilities.substring(1, rawAbilities.length - 1)
                    } else {
                        rawAbilities
                    }

                    val entity = CreatureEntity(
                        name = parts[1].trim(),
                        species = parts[2].trim(),
                        temperament = parts[3].trim(),
                        dailyExpenses = parts[4].trim().toBigDecimal(),
                        adoptionCost = parts[5].trim().toBigDecimal(),
                        magicalAbilities = cleanAbilities.split(",").map { it.trim() },
                        isAdopted = if (parts.size > 7) parts[7].trim().toBoolean() else false
                    )
                    creatureRepository.save(entity)
                }
            }
        }
        println("Loaded initial data from CSV into database.")
    }
}
