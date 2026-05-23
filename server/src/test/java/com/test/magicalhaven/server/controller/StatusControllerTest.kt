package com.test.magicalhaven.server.controller

import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

class StatusControllerTest {

    private val mockMvc = MockMvcBuilders.standaloneSetup(StatusController()).build()

    @Test
    fun `getStatus should return UP`() {
        mockMvc.perform(get("/api/v1/status"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("UP"))
    }
}
