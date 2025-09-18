package com.coding.newsapp.ui.setting

import com.coding.newsapp.ui.utils.Theme
import com.coding.newsapp.ui.utils.Language

sealed interface SettingScreenAction {
    data class OnThemeChange(val theme: Theme) : SettingScreenAction
    data class OnLanguageChange(val language: Language) : SettingScreenAction
    data object OnRetryClick : SettingScreenAction
    data object OnDeleteAllClick : SettingScreenAction
    data object OnLogOutClick : SettingScreenAction
}
