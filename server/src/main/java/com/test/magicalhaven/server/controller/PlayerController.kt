package com.test.magicalhaven.server.controller

import com.test.magicalhaven.server.model.Player
import com.test.magicalhaven.server.service.PlayerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/players")
@Tag(name = "Players", description = "Player information and owned creatures")
class PlayerController(private val playerService: PlayerService) {

    @GetMapping("/me")
    @Operation(summary = "Get current player info and adopted creatures")
    fun getMe(): ResponseEntity<Player> {
        val username = SecurityContextHolder.getContext().authentication.name
        val player = playerService.getPlayer(username) ?: playerService.createPlayer(username)
        return ResponseEntity.ok(player)
    }
}
