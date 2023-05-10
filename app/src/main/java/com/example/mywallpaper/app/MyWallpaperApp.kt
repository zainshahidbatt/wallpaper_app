package com.example.mywallpaper.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.example.mywallpaper.common.resourceManagerModule
import com.example.mywallpaper.data.datasource.dataSourceModule
import com.example.mywallpaper.data.db.databaseModule
import com.example.mywallpaper.data.network.networkModule
import com.example.mywallpaper.data.prefs.prefsModule
import com.example.mywallpaper.data.repository.repositoryModule
import com.example.mywallpaper.domain.tools.toolsModule
import com.example.mywallpaper.domain.usecase.useCaseModule
import com.example.mywallpaper.presentation.screen.categoryphotos.categoryPhotosModule
import com.example.mywallpaper.presentation.screen.curatedphotos.curatedPhotosModule
import com.greylabsdev.pexwalls.presentation.screen.favorites.favoritesModule
import com.example.mywallpaper.presentation.screen.home.homeModule
import com.example.mywallpaper.presentation.screen.photo.photoModule
import com.example.mywallpaper.presentation.screen.search.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import timber.log.Timber

class MyWallpaperApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@MyWallpaperApp)
            modules(
                listOf(
                    // data
                    databaseModule,
                    dataSourceModule,
                    networkModule,
                    prefsModule,
                    repositoryModule,

                    // tools
                    resourceManagerModule,
                    toolsModule,

                    // screens
                    useCaseModule,
                    categoryPhotosModule,
                    curatedPhotosModule,
                    homeModule,
                    searchModule,
                    photoModule,
                    favoritesModule
                )
            )
        }
    }
}
