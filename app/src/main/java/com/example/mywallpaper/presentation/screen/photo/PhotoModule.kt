package com.example.mywallpaper.presentation.screen.photo

import com.example.mywallpaper.presentation.model.PhotoModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val photoModule = module {
    viewModel { (photoModel: PhotoModel) -> PhotoViewModel(get(), get(), photoModel) }
}
