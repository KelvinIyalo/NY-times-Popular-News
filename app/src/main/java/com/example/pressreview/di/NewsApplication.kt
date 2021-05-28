package com.example.pressreview.di

import android.app.Application
import com.example.pressreview.data.NewsArticle
import com.example.pressreview.network.NewsApi
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Response
import javax.inject.Inject

@HiltAndroidApp()
class NewsApplication:Application() {

}