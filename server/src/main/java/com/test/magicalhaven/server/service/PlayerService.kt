package com.test.magicalhaven.server.service

import com.test.magicalhaven.server.mapper.PlayerMapper
import com.test.magicalhaven.server.model.Player
import com.test.magicalhaven.server.repository.PlayerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val playerMapper: PlayerMapper
) {
    fun getPlayer(username: String): Player? {
        return playerRepository.findByUsername(username)
            .map { playerMapper.toDomain(it) }
            .orElse(null)
    }

    @Transactional
    fun createPlayer(username: String): Player {
        val existing = playerRepository.findByUsername(username)
        if (existing.isPresent) return playerMapper.toDomain(existing.get())
        
        val player = com.test.magicalhaven.server.repository.entity.PlayerEntity(
            username = username,
            balance = java.math.BigDecimal("10000.00")
        )
        return playerMapper.toDomain(playerRepository.save(player))
    }
}
