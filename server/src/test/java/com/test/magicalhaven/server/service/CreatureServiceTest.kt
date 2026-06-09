package com.test.magicalhaven.server.service

import com.test.magicalhaven.server.mapper.CreatureEntityMapper
import com.test.magicalhaven.server.model.Creature
import com.test.magicalhaven.server.repository.CreatureRepository
import com.test.magicalhaven.server.repository.entity.CreatureEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class CreatureServiceTest {

    @Mock
    private lateinit var creatureRepository: CreatureRepository

    @Mock
    private lateinit var creatureEntityMapper: CreatureEntityMapper

    @InjectMocks
    private lateinit var creatureService: CreatureService

    private lateinit var testEntity: CreatureEntity
    private lateinit var testDomain: Creature

    @BeforeEach
    fun setUp() {
        testEntity = CreatureEntity(
            id = 1L,
            name = "Test",
            species = "TestSpecies",
            temperament = "Calm",
            dailyExpenses = BigDecimal("50.00"),
            adoptionCost = BigDecimal("1000.00"),
            magicalAbilities = listOf("TestAbility"),
            isAdopted = false
        )

        testDomain = Creature(
            id = 1L,
            name = "Test",
            species = "TestSpecies",
            temperament = "Calm",
            dailyExpenses = BigDecimal("50.00"),
            adoptionCost = BigDecimal("1000.00"),
            magicalAbilities = listOf("TestAbility"),
            isAdopted = false
        )
    }

    @Test
    fun `getAllCreatures returns mapped list`() {
        `when`(creatureRepository.findAll()).thenReturn(listOf(testEntity))
        `when`(creatureEntityMapper.toDomain(testEntity)).thenReturn(testDomain)

        val result = creatureService.getAllCreatures()

        assertEquals(1, result.size)
        assertEquals(testDomain, result[0])
    }

    @Test
    fun `getAvailableCreatures returns only not adopted`() {
        `when`(creatureRepository.findByIsAdoptedFalse()).thenReturn(listOf(testEntity))
        `when`(creatureEntityMapper.toDomain(testEntity)).thenReturn(testDomain)

        val result = creatureService.getAvailableCreatures()

        assertEquals(1, result.size)
        assertEquals(false, result[0].isAdopted)
    }

    @Test
    fun `adoptCreature updates entity and returns domain`() {
        `when`(creatureRepository.findById(1L)).thenReturn(java.util.Optional.of(testEntity))
        `when`(creatureEntityMapper.toDomain(testEntity)).thenReturn(testDomain)
        `when`(creatureRepository.save(testEntity)).thenReturn(testEntity)

        val result = creatureService.adoptCreature(1L)

        assertNotNull(result)
        assertEquals(true, testEntity.isAdopted)
        verify(creatureRepository).save(testEntity)
    }

    @Test
    fun `adoptCreature returns null when not found`() {
        `when`(creatureRepository.findById(1L)).thenReturn(java.util.Optional.empty())

        val result = creatureService.adoptCreature(1L)

        assertNull(result)
    }

    @Test
    fun `adoptCreature returns null when already adopted`() {
        val adoptedEntity = testEntity.copy(isAdopted = true)
        `when`(creatureRepository.findById(1L)).thenReturn(java.util.Optional.of(adoptedEntity))

        val result = creatureService.adoptCreature(1L)

        assertNull(result)
    }
}