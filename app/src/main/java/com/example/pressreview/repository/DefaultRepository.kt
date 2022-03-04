package com.example.pressreview.repository

import com.example.pressreview.constants.Resource
import com.example.pressreview.module.NYTimesData
import retrofit2.Response

interface DefaultRepository {

    suspend fun getHeadline(): Response<NYTimesData>
}