package com.example.mywallpaper.data.db

import androidx.room.TypeConverter
import com.example.mywallpaper.data.db.entity.PhotoDbEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DbConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringPhoto(source: String): PhotoDbEntity {
        val type = object : TypeToken<PhotoDbEntity>() {}.type
        return gson.fromJson(source, type)
    }

    @TypeConverter
    fun toString(source: PhotoDbEntity): String {
        val type = object : TypeToken<PhotoDbEntity>() {}.type
        return gson.toJson(source, type)
    }
}
