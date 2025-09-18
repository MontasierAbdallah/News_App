package com.coding.newsapp.di



import com.coding.data.network.KtorRemoteNewsDataSource
import com.coding.data.network.RemoteNewsDataSource
import com.coding.data.repository.NewsRepositoryImp
import com.coding.domain.repository.NewsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val repositoryModule = module {
    singleOf(::KtorRemoteNewsDataSource).bind<RemoteNewsDataSource>()

    singleOf(::NewsRepositoryImp).bind<NewsRepository>()




}
