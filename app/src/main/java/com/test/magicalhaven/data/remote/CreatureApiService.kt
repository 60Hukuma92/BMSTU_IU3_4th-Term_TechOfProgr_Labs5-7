package com.test.magicalhaven.data.remote

import com.test.magicalhaven.domain.model.Creature
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CreatureApiService {
    @GET("api/v1/shelter/creatures/available")
    suspend fun getAvailableCreatures(): List<Creature>

    @POST("api/v1/shelter/creatures/{id}/adopt")
    suspend fun adoptCreature(@Path("id") id: String): Creature
}

interface StatusApiService {
    @GET("api/v1/status")
    suspend fun getStatus(): Map<String, String>
}
