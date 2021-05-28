package com.example.pressreview.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pressreview.constants.Resource
import com.example.pressreview.data.NewsArticle
import com.example.pressreview.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MyViewModel @ViewModelInject constructor(private val repository: Repository):ViewModel() {

    val myBreakingNews:MutableLiveData<Resource<NewsArticle>> = MutableLiveData()
    val myQuery:MutableLiveData<Resource<NewsArticle>> = MutableLiveData()

    fun getHeadlines(Country:String){
        viewModelScope.launch {
            myBreakingNews.postValue(Resource.Loading())
            val breakingResponse = repository.getHeadline(Country)
            myBreakingNews.postValue(handleBreakingNews(breakingResponse))
        }


    }

    fun handleBreakingNews(response:Response<NewsArticle>):Resource<NewsArticle>{
        if (response.isSuccessful){
            response.body()?.let { ResultResponse ->
            return Resource.Success(ResultResponse)
            }
        }
       return Resource.Error(response.message(),data = null)
    }



    //Query

    fun getQuery(Query:String){
        viewModelScope.launch {
            myQuery.postValue(Resource.Loading())
            val queryResponse = repository.getHeadline(Query)
            myQuery.postValue(handleQuery(queryResponse))
        }


    }

    fun handleQuery(response:Response<NewsArticle>):Resource<NewsArticle>{
        if (response.isSuccessful){
            response.body()?.let { ResultResponse ->
                return Resource.Success(ResultResponse)
            }
        }
        return Resource.Error(response.message(),data = null)
    }



}