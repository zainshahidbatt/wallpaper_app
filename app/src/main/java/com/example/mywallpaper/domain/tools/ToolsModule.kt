package com.example.mywallpaper.domain.tools

import org.koin.dsl.module

val toolsModule = module {
    single { ResolutionManager(get()) }
    single { WallpaperSetter(get()) }
}
