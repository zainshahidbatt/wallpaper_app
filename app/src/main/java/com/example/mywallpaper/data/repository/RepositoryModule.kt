package com.example.mywallpaper.data.repository

import com.example.mywallpaper.domain.repository.IRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<IRepository> { Repository(get(named("local")), get(named("remote"))) }
}
