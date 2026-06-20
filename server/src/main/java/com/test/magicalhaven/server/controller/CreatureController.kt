package com.test.magicalhaven.server.controller

import com.test.magicalhaven.server.model.Creature
import com.test.magicalhaven.server.service.CreatureService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/creatures")
@Tag(name = "Creatures", description = "CRUD operations for magical creatures")
class CreatureController(private val creatureService: CreatureService) {

    @GetMapping
    @Operation(summary = "Get all creatures")
    fun getAll(): List<Creature> = creatureService.getAllCreatures()

    @GetMapping("/{id}")
    @Operation(summary = "Get creature by ID")
    fun getById(@PathVariable id: Long): ResponseEntity<Creature> {
        val creature = creatureService.getById(id)
        return if (creature != null) ResponseEntity.ok(creature)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    @Operation(summary = "Create a new creature")
    fun create(@RequestBody creature: Creature): ResponseEntity<Creature> {
        return ResponseEntity.status(HttpStatus.CREATED).body(creatureService.create(creature))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing creature")
    fun update(@PathVariable id: Long, @RequestBody creature: Creature): ResponseEntity<Creature> {
        val updated = creatureService.update(id, creature)
        return if (updated != null) ResponseEntity.ok(updated)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a creature")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        return if (creatureService.delete(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/{id}/adopt")
    @Operation(summary = "Adopt a creature")
    fun adopt(@PathVariable id: Long): ResponseEntity<Creature> {
        val username = SecurityContextHolder.getContext().authentication.name
        val adopted = creatureService.adoptCreature(id, username)
        return if (adopted != null) ResponseEntity.ok(adopted)
        else ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @GetMapping("/available")
    @Operation(summary = "Get all available (not adopted) creatures")
    fun getAvailable(): List<Creature> = creatureService.getAvailableCreatures()
}
