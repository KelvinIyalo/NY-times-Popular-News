package com.example.pressreview.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.pressreview.constants.Resource
import com.example.pressreview.data.Article
import com.example.pressreview.data.NewsArticle
import com.example.pressreview.di.NewsApplication
import com.example.pressreview.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MyViewModel @ViewModelInject constructor(private val app:Application,private val repository: Repository):
    AndroidViewModel(app) {

    val myBreakingNews:MutableLiveData<Resource<NewsArticle>> = MutableLiveData()
    val myQuery:MutableLiveData<Resource<NewsArticle>> = MutableLiveData()

    init {
        getHeadlines("ng")
    }


    fun getHeadlines(Country:String){
        viewModelScope.launch {
            myBreakingNews.postValue(Resource.Loading())
            safeHeadlineNewscall(Country)

        }


    }

  private  fun handleBreakingNews(response:Response<NewsArticle>):Resource<NewsArticle>{
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
           safeQuerry(Query)
        }


    }

  private  fun handleQuery(response:Response<NewsArticle>):Resource<NewsArticle>{
        if (response.isSuccessful){
            response.body()?.let { ResultResponse ->
                return Resource.Success(ResultResponse)
            }
        }
        return Resource.Error(response.message(),data = null)
    }


    //DataBase setup

    fun Updert(article: Article) = viewModelScope.launch {
        repository.Upsert(article)
    }

    fun delete(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }

    fun getAllFromDB():LiveData<List<Article>> = repository.getAllFromDB()

    suspend fun safeHeadlineNewscall(Country: String){
        myBreakingNews.postValue(Resource.Loading())
        try {
            if (isInternetAvailable()) {
                val breakingResponse = repository.getHeadline(Country)
                myBreakingNews.postValue(handleBreakingNews(breakingResponse))
            }else{
                myBreakingNews.postValue(Resource.Error("No Internet Connection", null))
            }

        }catch (t:Throwable){
            when(t){
                is IOException -> myBreakingNews.postValue(Resource.Error("Network Failure", null))
                else -> myBreakingNews.postValue(Resource.Error("Conversion Error:$t", null))
            }

        }

    }

    suspend fun safeQuerry(Querry: String){
        myQuery.postValue(Resource.Loading())
        try {
            if (isInternetAvailable()) {
                myQuery.postValue(Resource.Loading())
                val queryResponse = repository.getQuery(Querry)
                myQuery.postValue(handleQuery(queryResponse))
            }else{
                myQuery.postValue(Resource.Error("No Internet Connection", null))
            }

        }catch (t:Throwable){
            when(t){
                is IOException -> myQuery.postValue(Resource.Error("Network Failure", null))
                else -> myQuery.postValue(Resource.Error("Conversion Error:$t", null))
            }

        }

    }

    fun isInternetAvailable():Boolean{

        val connectivityManager = getApplication<NewsApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false

            }

        } else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false

                }
            }
        }
        return false

    }
}