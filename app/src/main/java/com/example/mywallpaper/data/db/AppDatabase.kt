package com.example.mywallpaper.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mywallpaper.data.db.dao.PhotoDao
import com.example.mywallpaper.data.db.entity.PhotoDbEntity

@Database(entities = [PhotoDbEntity::class], version = 1)
@TypeConverters(DbConverters::class)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}
