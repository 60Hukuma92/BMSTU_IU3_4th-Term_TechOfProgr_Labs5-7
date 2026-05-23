package com.test.magicalhaven.server.controller

import com.test.magicalhaven.server.aspect.RequiresRole
import com.test.magicalhaven.server.model.Creature
import com.test.magicalhaven.server.service.CreatureService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/v1/shelter/creatures")
class ShelterController(private val creatureService: CreatureService) {

    @GetMapping("/all")
    @RequiresRole("ADMIN")
    fun getAllCreatures(): List<Creature> {
        return creatureService.getAllCreatures()
    }

    @GetMapping("/available")
    fun getAvailableCreatures(): List<Creature> {
        return creatureService.getAvailableCreatures()
    }

    @PostMapping("/{id}/adopt")
    fun adoptCreature(@PathVariable id: Long): ResponseEntity<Creature> {
        val adopted = creatureService.adoptCreature(id)
        return if (adopted != null) {
            ResponseEntity.ok(adopted)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

