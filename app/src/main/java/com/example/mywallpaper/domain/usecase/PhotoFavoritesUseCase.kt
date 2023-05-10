package com.example.mywallpaper.domain.usecase

import com.example.mywallpaper.domain.entity.PhotoFavoriteEntity
import com.example.mywallpaper.domain.mapper.DomainMapper
import com.example.mywallpaper.domain.repository.IRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PhotoFavoritesUseCase(
    private val repository: IRepository
) {

    suspend fun getFavoritePhotos(): List<PhotoFavoriteEntity> {
        return withContext(IO) {
            repository.getAllFavoritePhotos().map { DomainMapper.mapToPhotoFavoriteEntity(it) }
        }
    }

    suspend fun addPhotoToFavorites(photo: PhotoFavoriteEntity) {
        withContext(IO) {
            repository.addPhotoToFavorites(DomainMapper.mapToDbEntity(photo))
        }
    }

    suspend fun removePhotoFromFavorites(photo: PhotoFavoriteEntity) {
        withContext(IO) {
            repository.removePhotoFromFavorites(DomainMapper.mapToDbEntity(photo))
        }
    }

    suspend fun checkIfPhotoInFavorites(id: Int): Boolean {
        return withContext(IO) {
            repository.checkIfPhotoInFavorites(id)
        }
    }
}
