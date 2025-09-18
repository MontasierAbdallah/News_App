package com.coding.newsapp.ui.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.domain.repository.NewsRepository
import com.coding.newsapp.ui.utils.AppPreferences
import com.coding.newsapp.ui.utils.Language
import com.coding.newsapp.ui.utils.Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(
    private val appPreferences: AppPreferences,
    private val  newsRepository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    init {
        loadSettings()
    }



    private fun loadSettings() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val theme = Theme.valueOf(appPreferences.getTheme())
            val language = Language.valueOf(appPreferences.getLanguage())

            _state.update {
                it.copy(
                    currentTheme = theme,
                    currentLanguage = language,
                    isLoading = false,
                    errorMessage = null
                )
            }
        } catch (e: Exception) {
            Log.e("SettingViewModel", "Error loading settings", e)
            _state.update {
                it.copy(isLoading = false, errorMessage = e.localizedMessage)
            }
        }
    }

    fun onAction(action: SettingScreenAction) {
        when (action) {
            is SettingScreenAction.OnThemeChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    appPreferences.changeThemeMode(action.theme.name)
                    _state.update { it.copy(currentTheme = action.theme) }
                }
            }

            is SettingScreenAction.OnLanguageChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    appPreferences.changeLanguageMode(action.language.name)
                    _state.update { it.copy(currentLanguage = action.language) }
                }
            }

            SettingScreenAction.OnRetryClick -> {
                loadSettings()
            }

            SettingScreenAction.OnDeleteAllClick -> {
                viewModelScope.launch {
                    newsRepository.deleteAllArticles()
                }

            }
            SettingScreenAction.OnLogOutClick -> {}
        }
    }
}
