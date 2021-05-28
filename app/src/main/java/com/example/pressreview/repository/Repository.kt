package com.example.pressreview.repository

import com.example.pressreview.data.NewsArticle
import com.example.pressreview.network.NewsApi
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(val api: NewsApi) {


    suspend fun getHeadline(Country:String): Response<NewsArticle> {
        return api.getHeadLines(Country)
    }

    suspend fun getQuery(Query:String): Response<NewsArticle> {
        return api.getQuerry(Query)
    }
}