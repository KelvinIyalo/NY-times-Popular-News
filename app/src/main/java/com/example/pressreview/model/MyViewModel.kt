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
import com.example.pressreview.constants.Event
import com.example.pressreview.constants.Resource
import com.example.pressreview.di.NewsApplication
import com.example.pressreview.module.NYTimesData
import com.example.pressreview.repository.DefaultRepository
import com.example.pressreview.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MyViewModel @ViewModelInject constructor(
    private val app: Application,
    private val defaultRepository: DefaultRepository
) :
    AndroidViewModel(app) {


    val myBreakingNews: MutableLiveData<Resource<NYTimesData>> = MutableLiveData()


    init {
              getHeadlines()
    }


    fun getHeadlines() {
        viewModelScope.launch {
            myBreakingNews.postValue(Resource.Loading())
            safeHeadlineNewscall()

        }


    }

    private fun handleBreakingNews(response: Response<NYTimesData>): Resource<NYTimesData> {
        if (response.isSuccessful) {
            response.body()?.let { ResultResponse ->
                return Resource.Success(ResultResponse)
            }
        }
        return Resource.Error(response.message(), data = null)
    }

    suspend fun safeHeadlineNewscall() {
        myBreakingNews.postValue(Resource.Loading())

        try {
            if (isInternetAvailable()) {
                val breakingResponse = defaultRepository.getHeadline()
                myBreakingNews.postValue(handleBreakingNews(breakingResponse))
            } else {
                myBreakingNews.postValue(Resource.Error("No Internet Connection", null))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> myBreakingNews.postValue(Resource.Error("Network Failure", null))
                else -> myBreakingNews.postValue(Resource.Error("Conversion Error:$t", null))
            }

        }
    }

    //Network Connectivity Check

    fun isInternetAvailable(): Boolean {

        val connectivityManager =
            getApplication<NewsApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false

            }

        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
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