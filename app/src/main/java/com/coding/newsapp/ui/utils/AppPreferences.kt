package com.coding.newsapp.ui.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map



class AppPreferences(
    private val dataStore: DataStore<Preferences>
) {

    private val themeKey = stringPreferencesKey("com/coding/newsapp/theme")
    private val languageKey = stringPreferencesKey("com/coding/newsapp/language")


    suspend fun getTheme() = dataStore.data.map { preferences ->
        preferences[themeKey] ?: Theme.DARK_MODE.name
    }.first()
    suspend fun getLanguage() = dataStore.data.map { preferences ->
        preferences[languageKey] ?: Language.DEFAULT_LANGUAGE.name
    }.last()

    suspend fun changeThemeMode(value: String) = dataStore.edit { preferences ->
        preferences[themeKey] = value
    }
    suspend fun changeLanguageMode(value: String) = dataStore.edit { preferences ->
        preferences[languageKey] = value
    }

}