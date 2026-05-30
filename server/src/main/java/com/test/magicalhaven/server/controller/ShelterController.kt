package com.test.magicalhaven.server.controller

import com.test.magicalhaven.server.aspect.RequiresRole
import com.test.magicalhaven.server.dto.CreatureDto
import com.test.magicalhaven.server.mapper.CreatureDtoMapper
import com.test.magicalhaven.server.service.CreatureService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/shelter/creatures")
class ShelterController(
    private val creatureService: CreatureService,
    private val creatureDtoMapper: CreatureDtoMapper
) {

    @GetMapping("/all")
    @RequiresRole("ADMIN")
    fun getAllCreatures(): List<CreatureDto> {
        return creatureService.getAllCreatures().map(creatureDtoMapper::toDto)
    }

    @GetMapping("/available")
    fun getAvailableCreatures(): List<CreatureDto> {
        return creatureService.getAvailableCreatures().map(creatureDtoMapper::toDto)
    }

    @PostMapping("/{id}/adopt")
    fun adoptCreature(@PathVariable id: Long): ResponseEntity<CreatureDto> {
        val adopted = creatureService.adoptCreature(id)
        return if (adopted != null) {
            ResponseEntity.ok(creatureDtoMapper.toDto(adopted))
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
