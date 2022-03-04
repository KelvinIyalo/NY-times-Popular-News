package com.example.pressreview.repository


import com.example.pressreview.constants.Resource
import com.example.pressreview.module.NYTimesData
import com.example.pressreview.network.NewsApi
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val api: NewsApi):DefaultRepository {



    override suspend fun getHeadline():Response<NYTimesData>{

        return api.getHeadLines()
    }


}