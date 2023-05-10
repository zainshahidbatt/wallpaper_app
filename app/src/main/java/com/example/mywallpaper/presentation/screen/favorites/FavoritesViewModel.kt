package com.greylabsdev.pexwalls.presentation.screen.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mywallpaper.domain.usecase.PhotoFavoritesUseCase
import com.example.mywallpaper.presentation.base.BaseViewModel
import com.example.mywallpaper.presentation.base.ProgressState
import com.example.mywallpaper.presentation.collection.PhotoPagingUpdater
import com.example.mywallpaper.presentation.collection.UpdaterType
import com.example.mywallpaper.presentation.model.PhotoModel
import com.example.mywallpaper.presentation.paging.PagingItem
import com.example.mywallpaper.presentation.paging.PagingUpdater

class FavoritesViewModel(
    favoritesUseCase: PhotoFavoritesUseCase
) : BaseViewModel() {

    val photos: LiveData<List<PagingItem<PhotoModel>>>
        get() = photoGridPagingUpdater.pagingDataSource.itemsChannelLiveData

    var photoGridPagingUpdater: PagingUpdater<PhotoModel> =
        PhotoPagingUpdater(
            photoFavoritesUseCase = favoritesUseCase,
            type = UpdaterType.FAVORITES,
            emptyResultListener = { _progressState.value = ProgressState.EMPTY() },
            errorListener = { error -> _progressState.value = ProgressState.ERROR(error) },
            viewModelScope = viewModelScope
        )
}
