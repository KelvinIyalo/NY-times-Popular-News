package com.example.pressreview.network


import com.example.pressreview.constants.API_KEY
import com.example.pressreview.constants.Resource

import com.example.pressreview.module.NYTimesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("7.json")
    suspend fun getHeadLines(
            @Query("api-key")APIKEY:String = API_KEY

    ): Response<NYTimesData>

}