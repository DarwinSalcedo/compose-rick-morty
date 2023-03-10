package com.paging.compose.domain.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("/api/character/?")
    suspend fun getListCharacters(
        @Query("page") page: Int,
    ): Characters

}