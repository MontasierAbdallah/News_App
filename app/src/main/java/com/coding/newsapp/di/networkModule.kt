package com.coding.newsapp.di

import com.coding.data.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val networkModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<HttpClient> { HttpClientFactory.create(get()) }
}
