package com.test.magicalhaven.server.repository

import com.test.magicalhaven.server.repository.entity.CreatureEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.math.BigDecimal

@DataJpaTest
class CreatureRepositoryTest {

    @Autowired
    lateinit var creatureRepository: CreatureRepository

    @Test
    fun `should save and find creature`() {
        val creature = CreatureEntity(
            name = "Test Dragon",
            species = "Dragon",
            temperament = "Fiery",
            dailyExpenses = BigDecimal("100.00"),
            adoptionCost = BigDecimal("500.00"),
            magicalAbilities = listOf("Fire"),
            isAdopted = false
        )

        val saved = creatureRepository.save(creature)
        val found = creatureRepository.findById(saved.id).orElse(null)

        assertThat(found).isNotNull
        assertThat(found?.name).isEqualTo("Test Dragon")
    }

    @Test
    fun `should find only not adopted creatures`() {
        creatureRepository.save(CreatureEntity(name = "C1", isAdopted = false, species = "S", temperament = "T"))
        creatureRepository.save(CreatureEntity(name = "C2", isAdopted = true, species = "S", temperament = "T"))

        val available = creatureRepository.findByIsAdoptedFalse()

        assertThat(available).hasSize(1)
        assertThat(available[0].name).isEqualTo("C1")
    }
}
