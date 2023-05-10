package com.example.mywallpaper.data.datasource

import com.example.mywallpaper.data.db.entity.PhotoDbEntity
import com.example.mywallpaper.data.dto.SearchResultDto

interface IDataSource {

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto?
    suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto?

    suspend fun addPhotoToFavorites(photoEntity: PhotoDbEntity)
    suspend fun removePhotoFromFavorites(photoEntity: PhotoDbEntity)
    suspend fun checkIfPhotoInFavorites(id: Int): Boolean
    suspend fun getPhotoById(id: Int): PhotoDbEntity
    suspend fun getAllPhotos(): List<PhotoDbEntity>
}
