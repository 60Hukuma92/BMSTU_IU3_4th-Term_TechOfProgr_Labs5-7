package com.test.magicalhaven.server.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import java.io.ByteArrayInputStream

class CreatureServiceTest {

    @Test
    fun `should load creatures from csv`() {
        val resourceLoader = mock(ResourceLoader::class.java)
        val resource = mock(Resource::class.java)
        
        val csvContent = """
            id,name,species,temperament,dailyExpenses,adoptionCost,magicalAbilities,isAdopted
            1,TestName,TestSpecies,TestTemp,10.0,20.0,"Ability1, Ability2",false
        """.trimIndent()
        
        `when`(resourceLoader.getResource(anyString())).thenReturn(resource)
        `when`(resource.exists()).thenReturn(true)
        `when`(resource.inputStream).thenReturn(ByteArrayInputStream(csvContent.toByteArray()))

        val service = CreatureService(resourceLoader, "test.csv")
        service.afterPropertiesSet()

        val creatures = service.getAllCreatures()
        assertEquals(1, creatures.size)
        assertEquals("TestName", creatures[0].name)
        assertEquals(2, creatures[0].magicalAbilities.size)
    }
}
