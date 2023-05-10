package com.example.mywallpaper.presentation.screen.home

import com.example.mywallpaper.presentation.screen.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}
