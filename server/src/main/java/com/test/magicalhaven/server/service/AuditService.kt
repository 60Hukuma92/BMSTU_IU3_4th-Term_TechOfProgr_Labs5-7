package com.test.magicalhaven.server.service

import com.test.magicalhaven.server.model.ActionLog
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class AuditService {
    private val logs = mutableListOf<ActionLog>()
    private val idCounter = AtomicLong(1)

    fun logAction(action: String, role: String) {
        val log = ActionLog(
            id = idCounter.getAndIncrement(),
            action = action,
            role = role
        )
        logs.add(log)
        println("AUDIT: user with role [\$role] executed action: \$action")
    }

    fun getAllLogs(): List<ActionLog> = logs.toList()
}
