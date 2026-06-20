package com.test.magicalhaven.server.controller

import com.test.magicalhaven.server.config.JwtService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/auth")
class AuthController(private val jwtService: JwtService) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): Map<String, String> {
        // Simple auth for the lab: any user with password 'password' gets a token
        if (request.password == "password") {
            return mapOf("token" to jwtService.generateToken(request.username))
        }
        throw RuntimeException("Invalid credentials")
    }
}

data class LoginRequest(val username: String, val password: String)
