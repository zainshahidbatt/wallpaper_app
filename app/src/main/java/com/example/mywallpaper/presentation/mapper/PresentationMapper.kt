package com.example.mywallpaper.presentation.mapper

import com.example.mywallpaper.domain.entity.PhotoEntity
import com.example.mywallpaper.domain.entity.PhotoFavoriteEntity
import com.example.mywallpaper.presentation.model.PhotoModel

object PresentationMapper {
    fun mapToPhotoModel(photo: PhotoEntity): PhotoModel {
        return PhotoModel(
            photo.id,
            photo.src.large,
            photo.src.large2x,
            photo.src.byScreenResolutionUrl,
            photo.photographer,
            photo.photographerUrl,
            photo.width,
            photo.height
        )
    }

    fun mapToPhotoModel(photo: PhotoFavoriteEntity): PhotoModel {
        return PhotoModel(
            photo.id,
            photo.normalPhotoUrl,
            photo.normalPhotoUrl,
            photo.byScreenResolutionUrl,
            photo.photographer,
            photo.photographerUrl,
            photo.width,
            photo.height
        )
    }

    fun mapToEntity(photo: PhotoModel): PhotoFavoriteEntity {
        return PhotoFavoriteEntity(
            photo.id,
            photo.normalPhotoUrl,
            photo.bigPhotoUrl,
            photo.byScreenResolutionUrl,
            photo.photographer,
            photo.photographerUrl,
            photo.width,
            photo.height
        )
    }
}
