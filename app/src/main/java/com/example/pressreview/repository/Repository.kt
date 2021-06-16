package com.example.pressreview.repository

import androidx.lifecycle.LiveData
import com.example.pressreview.data.Article
import com.example.pressreview.data.NewsArticle
import com.example.pressreview.db.NewsDao
import com.example.pressreview.network.NewsApi
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val dao: NewsDao,private val api: NewsApi) {


    suspend fun getHeadline(Country:String): Response<NewsArticle> {
        return api.getHeadLines(Country)
    }

    suspend fun getQuery(Query:String): Response<NewsArticle> {
        return api.getQuerry(Query)
    }


    suspend fun Upsert(article: Article){
        dao.Upsert(article)
    }
    suspend fun delete(article: Article){
        dao.delete(article)
    }

    fun getAllFromDB():LiveData<List<Article>>{
        return dao.getFromDB()
    }


}