package com.example.pressreview.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pressreview.data.Article
import com.example.pressreview.data.Converters

@Database(entities = [Article::class],
version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase:RoomDatabase() {
    abstract fun newsDao():NewsDao
}