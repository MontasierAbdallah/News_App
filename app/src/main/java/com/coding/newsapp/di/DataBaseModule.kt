package com.coding.newsapp.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.coding.data.database.entity.BookMarkEntity

import com.coding.data.datastore.AppSettings
import com.coding.newsapp.ui.utils.AppPreferences

import com.coding.newsapp.ui.utils.dataStoreFileName
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.android.ext.koin.androidApplication

import org.koin.dsl.module

val dataBaseModule = module {

    single {
        AppPreferences(dataStorePreferences(androidApplication()))
    }
    single {
        provideRealm()
    }


}
fun provideRealm(): Realm {
    val config= RealmConfiguration.Builder(
        schema = setOf(BookMarkEntity::class)
    ).compactOnLaunch()
        .build()

    return Realm.open(configuration = config)
}

fun dataStorePreferences(appContext: Application): DataStore<Preferences> {
    return AppSettings.getDataStore {
        appContext.filesDir
            .resolve(dataStoreFileName)
            .absolutePath
    }
}


