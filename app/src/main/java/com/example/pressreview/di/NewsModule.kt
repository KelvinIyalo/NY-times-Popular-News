package com.example.pressreview.di


import android.content.Context
import androidx.room.Room
import com.example.pressreview.constants.BASE_URL
import com.example.pressreview.db.NewsDao
import com.example.pressreview.db.NewsDatabase
import com.example.pressreview.network.NewsApi
import com.example.pressreview.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NewsModule {

        @Singleton
        @Provides
        fun ProvidesDataBase(
                @ApplicationContext context: Context) = Room.databaseBuilder(
                context,
                NewsDatabase::class.java, "news_db"
        ).build()

        @Singleton
        @Provides
        fun provideShoppingDao(database: NewsDatabase) = database.newsDao()

        @Singleton
        @Provides
        fun provideRepository(
                dao: NewsDao,
                api: NewsApi
        ) = Repository(dao,api)


        @Singleton
        @Provides
        fun provideCurrencyApi(): NewsApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApi::class.java)

}