package com.example.mywallpaper.common

import org.koin.dsl.module

val resourceManagerModule = module {

    single { ResManager(get()) }
}
