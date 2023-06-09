package com.example.mywallpaper.presentation.screen.search

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchViewModel(get()) }
}
