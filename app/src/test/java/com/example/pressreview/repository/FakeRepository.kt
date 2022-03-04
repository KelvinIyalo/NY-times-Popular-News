package com.example.pressreview.repository

import com.example.pressreview.constants.Resource
import com.example.pressreview.module.NYTimesData

class FakeRepository : DefaultRepository{



    private var shouldReturnNetWorkError = false

    fun setShouldReturnNetWorkError(value:Boolean){
        shouldReturnNetWorkError = value
    }
    override suspend fun getHeadline(): Resource<NYTimesData> {
return if (shouldReturnNetWorkError){
    Resource.Error("Error", null)
}else{
    Resource.Success(NYTimesData("",0, listOf(),""))
}
    }

}