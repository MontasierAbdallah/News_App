package com.coding.newsapp.ui.setting

import com.coding.newsapp.ui.utils.Theme
import com.coding.newsapp.ui.utils.Language

data class SettingScreenState(
    val currentTheme: Theme = Theme.SYSTEM_DEFAULT,
    val currentLanguage: Language = Language.DEFAULT_LANGUAGE,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
