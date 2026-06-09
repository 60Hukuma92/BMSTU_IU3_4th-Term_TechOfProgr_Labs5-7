package com.test.magicalhaven.server.repository

import com.test.magicalhaven.server.repository.entity.CreatureEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CreatureRepository : JpaRepository<CreatureEntity, Long> {

    fun findByIsAdoptedFalse(): List<CreatureEntity>
}