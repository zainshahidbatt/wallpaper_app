package com.example.mywallpaper.presentation.screen.curatedphotos

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val curatedPhotosModule = module {
    viewModel { CuratedPhotosViewModel(get()) }
}
