package com.example.mywallpaper.presentation.screen.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mywallpaper.domain.usecase.PhotoDisplayingUseCase
import com.example.mywallpaper.presentation.base.BaseViewModel
import com.example.mywallpaper.presentation.base.ProgressState
import com.example.mywallpaper.presentation.collection.PhotoPagingUpdater
import com.example.mywallpaper.presentation.collection.UpdaterType
import com.example.mywallpaper.presentation.model.PhotoModel
import com.example.mywallpaper.presentation.paging.PagingItem

class SearchViewModel(
    private val photoDisplayingUseCase: PhotoDisplayingUseCase
) : BaseViewModel() {

    val photos: LiveData<List<PagingItem<PhotoModel>>>
        get() = photoGridPagingUpdater.pagingDataSource.itemsChannelLiveData

    var photoGridPagingUpdater: PhotoPagingUpdater =
        PhotoPagingUpdater(
            photoDisplayingUseCase = photoDisplayingUseCase,
            type = UpdaterType.SEARCH,
            doneListener = { _progressState.value = ProgressState.DONE() },
            emptyResultListener = { _progressState.value = ProgressState.EMPTY() },
            errorListener = { error -> _progressState.value = ProgressState.ERROR(error) },
            viewModelScope = viewModelScope
        )

    init {
        _progressState.value = ProgressState.INITIAL()
    }

    fun search(searchQuery: String) {
        photoGridPagingUpdater.searchQuery = searchQuery
        photoGridPagingUpdater.resetAndFetchAgain()
    }
}
