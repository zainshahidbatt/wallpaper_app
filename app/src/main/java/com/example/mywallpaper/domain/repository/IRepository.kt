package com.example.mywallpaper.domain.repository

import com.example.mywallpaper.data.db.entity.PhotoDbEntity
import com.example.mywallpaper.data.dto.SearchResultDto

interface IRepository {

    suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto?

    suspend fun addPhotoToFavorites(photoEntity: PhotoDbEntity)
    suspend fun removePhotoFromFavorites(photoEntity: PhotoDbEntity)
    suspend fun checkIfPhotoInFavorites(id: Int): Boolean
    suspend fun getPhotoById(id: Int): PhotoDbEntity
    suspend fun getAllFavoritePhotos(): List<PhotoDbEntity>

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto?
}
