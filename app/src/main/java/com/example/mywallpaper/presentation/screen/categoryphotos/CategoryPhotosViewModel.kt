package com.example.mywallpaper.presentation.screen.categoryphotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mywallpaper.domain.usecase.PhotoDisplayingUseCase
import com.example.mywallpaper.presentation.base.BaseViewModel
import com.example.mywallpaper.presentation.base.ProgressState
import com.example.mywallpaper.presentation.collection.PhotoPagingUpdater
import com.example.mywallpaper.presentation.collection.UpdaterType
import com.example.mywallpaper.presentation.const.PhotoCategory
import com.example.mywallpaper.presentation.model.PhotoModel
import com.example.mywallpaper.presentation.paging.PagingItem
import com.example.mywallpaper.presentation.paging.PagingUpdater

class CategoryPhotosViewModel(
    photoDisplayingUseCase: PhotoDisplayingUseCase,
    photoCategory: PhotoCategory
) : BaseViewModel() {

    val photos: LiveData<List<PagingItem<PhotoModel>>>
        get() = photoGridPagingUpdater.pagingDataSource.itemsChannelLiveData

    var photoGridPagingUpdater: PagingUpdater<PhotoModel> =
        PhotoPagingUpdater(
            photoDisplayingUseCase = photoDisplayingUseCase,
            type = UpdaterType.CATEGORY,
            photoCategory = photoCategory,
            emptyResultListener = { _progressState.value = ProgressState.EMPTY() },
            errorListener = { error -> _progressState.value = ProgressState.ERROR(error) },
            viewModelScope = viewModelScope
        )

    fun repeatFetch() {
        _progressState.value = ProgressState.DONE()
        photoGridPagingUpdater.fetchPage(false)
    }
}
