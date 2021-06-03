package com.example.pressreview.network


import com.example.pressreview.constants.API_KEY
import com.example.pressreview.data.NewsArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getHeadLines(
            @Query("country")Country:String,
            @Query("apiKey")APIKEY:String = API_KEY

    ):Response<NewsArticle>

    @GET("everything")
    suspend fun getQuerry(
            @Query("q")QuerryParra:String,
            @Query("apiKey")APIKEY:String = API_KEY
    ):Response<NewsArticle>
}