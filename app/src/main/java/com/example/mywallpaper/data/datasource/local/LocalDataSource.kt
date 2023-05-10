package com.example.mywallpaper.data.datasource.local

import com.example.mywallpaper.data.datasource.IDataSource
import com.example.mywallpaper.data.db.AppDatabase
import com.example.mywallpaper.data.db.entity.PhotoDbEntity
import com.example.mywallpaper.data.dto.SearchResultDto

class LocalDataSource(private val appDatabase: AppDatabase) : IDataSource {

    override suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto? {
        throw Exception("Method only for RemoteDataSource realization")
    }

    override suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto? {
        throw Exception("Method only for RemoteDataSource realization")
    }

    override suspend fun addPhotoToFavorites(photoEntity: PhotoDbEntity) {
        appDatabase.photoDao().insert(photoEntity)
    }

    override suspend fun removePhotoFromFavorites(photoEntity: PhotoDbEntity) {
        appDatabase.photoDao().delete(photoEntity)
    }

    override suspend fun checkIfPhotoInFavorites(id: Int): Boolean {
        return appDatabase.photoDao().getById(id).isNotEmpty()
    }

    override suspend fun getPhotoById(id: Int): PhotoDbEntity {
        return appDatabase.photoDao().getById(id).first()
    }

    override suspend fun getAllPhotos(): List<PhotoDbEntity> {
        return appDatabase.photoDao().getAll()
    }
}
