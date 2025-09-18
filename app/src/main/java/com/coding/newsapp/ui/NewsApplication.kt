package com.coding.newsapp.ui

import android.app.Application
import com.coding.newsapp.di.dataBaseModule
import com.coding.newsapp.di.networkModule
import com.coding.newsapp.di.repositoryModule
import com.coding.newsapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NewsApplication)
        }
    }
}

fun initKoin(config: KoinAppDeclaration? = null) {
    val appModules = listOf(
        networkModule,
        repositoryModule,
        dataBaseModule,
        viewModelModule
    )

    startKoin {
        config?.invoke(this)
        modules(appModules)
    }
}
