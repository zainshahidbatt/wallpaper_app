package com.example.mywallpaper.domain.mapper

import com.example.mywallpaper.data.db.entity.PhotoDbEntity
import com.example.mywallpaper.data.dto.PhotoDto
import com.example.mywallpaper.domain.entity.PhotoEntity
import com.example.mywallpaper.domain.entity.PhotoFavoriteEntity
import com.example.mywallpaper.domain.entity.PhotoSrcEntity

object DomainMapper {

    fun mapToPhotoEntity(src: PhotoDto, byScreenResolution: String): PhotoEntity {
        return PhotoEntity(
            src.height,
            src.id,
            src.photographer,
            src.photographerId,
            src.photographerUrl,
            PhotoSrcEntity(
                src.src.landscape,
                src.src.large,
                src.src.large2x,
                src.src.medium,
                src.src.original,
                src.src.portrait,
                src.src.small,
                src.src.tiny,
                byScreenResolution
            ),
            src.url,
            src.width
        )
    }

    fun mapToPhotoFavoriteEntity(photo: PhotoDbEntity): PhotoFavoriteEntity {
        return PhotoFavoriteEntity(
            photo.id,
            photo.normalPhotoUrl,
            photo.bigPhotoUrl,
            photo.byScreenResolution,
            photo.photographer,
            photo.photographerUrl,
            photo.width,
            photo.height
        )
    }

    fun mapToDbEntity(photo: PhotoFavoriteEntity): PhotoDbEntity {
        return PhotoDbEntity(
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
