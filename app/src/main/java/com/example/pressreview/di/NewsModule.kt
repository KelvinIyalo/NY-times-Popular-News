package com.example.pressreview.di


import com.example.pressreview.constants.BASE_URL
import com.example.pressreview.network.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NewsModule {

        @Singleton
        @Provides
        fun provideCurrencyApi(): NewsApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApi::class.java)

}