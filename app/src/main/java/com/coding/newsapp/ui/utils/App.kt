package com.coding.newsapp.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.coding.newsapp.ui.MainScreen
import com.coding.newsapp.ui.setting.SettingViewModel
import com.coding.newsapp.ui.theme.NewsAppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        val settingViewModel = koinViewModel<SettingViewModel>()
        val currentTheme by settingViewModel.state.collectAsState()
        NewsAppTheme(currentTheme.currentTheme.name) {
            MainScreen(settingViewModel)
        }
    }
}