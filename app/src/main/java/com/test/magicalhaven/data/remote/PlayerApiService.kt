package com.test.magicalhaven.data.remote

import com.test.magicalhaven.domain.model.Player
import retrofit2.http.GET

interface PlayerApiService {
    @GET("api/v2/players/me")
    suspend fun getMyInfo(): Player
}
