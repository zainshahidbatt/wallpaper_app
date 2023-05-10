package com.example.mywallpaper.presentation.screen.curatedphotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mywallpaper.domain.usecase.PhotoDisplayingUseCase
import com.example.mywallpaper.presentation.base.BaseViewModel
import com.example.mywallpaper.presentation.base.ProgressState
import com.example.mywallpaper.presentation.collection.PhotoPagingUpdater
import com.example.mywallpaper.presentation.collection.UpdaterType
import com.example.mywallpaper.presentation.model.PhotoModel
import com.example.mywallpaper.presentation.paging.PagingItem
import com.example.mywallpaper.presentation.paging.PagingUpdater

class CuratedPhotosViewModel(photoDisplayingUseCase: PhotoDisplayingUseCase) : BaseViewModel() {

    val photos: LiveData<List<PagingItem<PhotoModel>>>
        get() = photoPagingUpdater.pagingDataSource.itemsChannelLiveData

    var photoPagingUpdater: PagingUpdater<PhotoModel> =
        PhotoPagingUpdater(
            photoDisplayingUseCase = photoDisplayingUseCase,
            type = UpdaterType.CURATED,
            emptyResultListener = { _progressState.value = ProgressState.EMPTY() },
            errorListener = { error -> _progressState.value = ProgressState.ERROR(error) },
            viewModelScope = viewModelScope
        )

    fun repeatFetch() {
        _progressState.value = ProgressState.DONE()
        photoPagingUpdater.fetchPage(false)
    }
}
