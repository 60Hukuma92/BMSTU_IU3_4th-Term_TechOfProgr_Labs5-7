package com.test.magicalhaven.server.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/status")
@Tag(name = "Status", description = "Application status check")
class StatusController {

    @GetMapping
    @Operation(summary = "Check application status")
    fun getStatus(): Map<String, String> {
        return mapOf("status" to "UP", "version" to "v2")
    }
}
