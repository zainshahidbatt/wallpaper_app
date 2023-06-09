package com.example.mywallpaper.domain.usecase

import org.koin.dsl.module

val useCaseModule = module {
    factory { PhotoDisplayingUseCase(get(), get()) }
    factory { PhotoDownloadingUseCase(get(), get(), get()) }
    factory { PhotoFavoritesUseCase(get()) }
}
