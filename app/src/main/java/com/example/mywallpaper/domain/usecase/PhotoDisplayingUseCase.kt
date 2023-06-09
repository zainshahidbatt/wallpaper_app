package com.example.mywallpaper.domain.usecase

import com.example.mywallpaper.domain.entity.PhotoEntity
import com.example.mywallpaper.domain.mapper.DomainMapper
import com.example.mywallpaper.domain.repository.IRepository
import com.example.mywallpaper.domain.tools.PhotoUrlGenerator
import com.example.mywallpaper.domain.tools.ResolutionManager
import com.example.mywallpaper.presentation.const.PhotoCategory
import com.example.mywallpaper.presentation.model.CategoryModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PhotoDisplayingUseCase(
    private val repository: IRepository,
    private val resolutionManager: ResolutionManager
) {

    private val photoUrlGenerator = PhotoUrlGenerator()

    suspend fun getPhotosForCategory(
        category: String,
        pageNumber: Int,
        perPageCount: Int
    ): List<PhotoEntity>? {
        return withContext(IO) {
            repository.searchPhotos(category, pageNumber, perPageCount)?.let {
                it.photos.map { photoDto ->
                    val byScreenResolution = photoUrlGenerator.generateUrl(
                        photoDto.src.large,
                        resolutionManager.screenResolution
                    )
                    DomainMapper.mapToPhotoEntity(photoDto, byScreenResolution)
                }
            }
        }
    }

    suspend fun getPhotoCategoryCover(category: PhotoCategory): CategoryModel? {
        return withContext(IO) {
            repository.searchPhotos(category.name, 1, 30)?.let {
                CategoryModel(category, it.photos.random().src.large)
            }
        }
    }

    suspend fun getCuratedPhotos(pageNumber: Int, perPageCount: Int): List<PhotoEntity>? {
        return withContext(IO) {
            repository.getCuratedPhotos(pageNumber, perPageCount)?.let {
                it.photos.map { photoDto ->
                    val byScreenResolution = photoUrlGenerator.generateUrl(
                        photoDto.src.large,
                        resolutionManager.screenResolution
                    )
                    DomainMapper.mapToPhotoEntity(photoDto, byScreenResolution)
                }
            }
        }
    }

    suspend fun searchPhotos(
        query: String,
        pageNumber: Int,
        perPageCount: Int
    ): List<PhotoEntity>? {
        return withContext(IO) {
            repository.searchPhotos(query, pageNumber, perPageCount)?.let {
                it.photos.map { photoDto ->
                    val byScreenResolution = photoUrlGenerator.generateUrl(
                        photoDto.src.large,
                        resolutionManager.screenResolution
                    )
                    DomainMapper.mapToPhotoEntity(photoDto, byScreenResolution)
                }
            }
        }
    }
}
