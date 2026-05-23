package com.test.magicalhaven.server.model

import java.time.LocalDateTime

data class ActionLog(
    val id: Long,
    val action: String,
    val role: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
