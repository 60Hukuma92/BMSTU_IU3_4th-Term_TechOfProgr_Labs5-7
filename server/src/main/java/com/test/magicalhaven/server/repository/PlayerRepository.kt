package com.test.magicalhaven.server.repository

import com.test.magicalhaven.server.repository.entity.PlayerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PlayerRepository : JpaRepository<PlayerEntity, Long> {
    fun findByUsername(username: String): Optional<PlayerEntity>
}
