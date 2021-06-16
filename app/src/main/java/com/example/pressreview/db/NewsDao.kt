package com.example.pressreview.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pressreview.data.Article

@Dao
interface NewsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Upsert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("Select * From ArticlesDataBase")
    fun getFromDB():LiveData<List<Article>>
}
