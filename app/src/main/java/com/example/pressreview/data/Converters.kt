package com.example.pressreview.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toString(source: Source):String?{
        return source.name

    }

    @TypeConverter
  fun  fromSource(name:String):Source{
        return Source(name,name)
    }
}