package com.example.mywallpaper.data.datasource

import com.example.mywallpaper.data.datasource.local.LocalDataSource
import com.example.mywallpaper.data.datasource.remote.RemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {

    single<IDataSource>(named("local")) { LocalDataSource(get()) }

    single<IDataSource>(named("remote")) { RemoteDataSource(get()) }
}
