package com.example.mywallpaper.presentation.screen.categoryphotos

import com.example.mywallpaper.presentation.const.PhotoCategory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryPhotosModule = module {
    viewModel { (photoCategory: PhotoCategory) -> CategoryPhotosViewModel(get(), photoCategory) }
}
