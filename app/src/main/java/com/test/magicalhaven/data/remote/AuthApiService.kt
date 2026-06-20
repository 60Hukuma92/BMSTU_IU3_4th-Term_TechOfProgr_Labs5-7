package com.test.magicalhaven.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/v2/auth/login")
    suspend fun login(@Body request: Map<String, String>): Map<String, String>
}
