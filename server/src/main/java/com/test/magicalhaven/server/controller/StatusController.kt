package com.test.magicalhaven.server.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/status")
class StatusController {

    @GetMapping
    fun getStatus(): Map<String, String> {
        return mapOf("status" to "UP")
    }
}

