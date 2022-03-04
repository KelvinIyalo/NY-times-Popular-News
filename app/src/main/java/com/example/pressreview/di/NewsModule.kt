package com.example.pressreview.di


import com.example.pressreview.constants.BASE_URL
import com.example.pressreview.network.NewsApi
import com.example.pressreview.repository.DefaultRepository
import com.example.pressreview.repository.Repository
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
    fun provideRepository(
        api: NewsApi
    ) = Repository(api) as DefaultRepository


    @Singleton
    @Provides
    fun provideCurrencyApi(): NewsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)

}