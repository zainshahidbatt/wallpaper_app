package com.example.mywallpaper.data.prefs

import org.koin.dsl.module

val prefsModule = module {
    single { AppPrefs(get()) }
}
