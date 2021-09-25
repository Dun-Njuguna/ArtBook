package com.example.artbook.api

import com.example.artbook.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/api/")
    suspend fun imageSearch(@Query("q") searchQuery: String) : Response<ImageResponse>
}