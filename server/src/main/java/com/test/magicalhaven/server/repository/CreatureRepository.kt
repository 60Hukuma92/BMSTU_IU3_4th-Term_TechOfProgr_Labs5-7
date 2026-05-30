package com.test.magicalhaven.server.repository

import com.test.magicalhaven.server.repository.entity.CreatureEntity

interface CreatureRepository {
    fun getAll(): List<CreatureEntity>
    fun findById(id: Long): CreatureEntity?
    fun update(creature: CreatureEntity): CreatureEntity
}

