package com.coding.newsapp.ui.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coding.newsapp.R
import com.coding.newsapp.ui.setting.component.LanguageSelectionDialog
import com.coding.newsapp.ui.setting.component.SettingItem
import com.coding.newsapp.ui.setting.component.ThemeSelectionDialog
import com.coding.newsapp.ui.theme.mediumPadding
import com.coding.newsapp.ui.setting.SettingScreenAction
import com.coding.newsapp.ui.setting.SettingScreenState
import com.coding.newsapp.ui.setting.SettingViewModel
import com.coding.newsapp.ui.setting.component.LogOutSelectionDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingScreenRoot(
    viewModel: SettingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingScreen(
        state = state,
        onAction = { action -> viewModel.onAction(action) }
    )
}

@Composable
fun SettingScreen(
    state: SettingScreenState,
    onAction: (SettingScreenAction) -> Unit
) {
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showLoginOutDialog by remember { mutableStateOf(false) }
    var showDeleteAllDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(mediumPadding)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.errorMessage != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(mediumPadding)
                ) {
                    Text(
                        text = state.errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { onAction(SettingScreenAction.OnRetryClick) }) {
                        Text(text = "Retry")
                    }
                }
            }

            else -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(mediumPadding)
                ) {
                    // Theme setting item
                    SettingItem(
                        onItemClick = { showThemeDialog = true },
                        itemPainter = painterResource(id = R.drawable.ic_theme),
                        itemName = "Theme"
                    )

                    // Language setting item
                    SettingItem(
                        onItemClick = { showLanguageDialog = true },
                        itemPainter = painterResource(id = R.drawable.ic_language),
                        itemName = "Language"
                    )
                    SettingItem(
                        onItemClick = { showLoginOutDialog = true },
                        itemPainter = painterResource(id = R.drawable.baseline_login_24),
                        itemName = "Log Out"
                    )
                    SettingItem(
                        onItemClick = { showDeleteAllDialog = true },
                        itemPainter = painterResource(id = R.drawable.ic_delete),
                        itemName = "Delete All BookMarks News"
                    )
                }
            }
        }


        if (showThemeDialog) {
            ThemeSelectionDialog(
                currentTheme = state.currentTheme.name,
                onDismissRequest = { showThemeDialog = false },
                onThemeChange = { theme ->
                    onAction(SettingScreenAction.OnThemeChange(theme))
                    showThemeDialog = false
                }
            )
        }

        if (showLanguageDialog) {
            LanguageSelectionDialog(
                currentLanguage = state.currentLanguage.name,
                onDismissRequest = { showLanguageDialog = false },
                onLanguageChange = { language ->
                    onAction(SettingScreenAction.OnLanguageChange(language))
                    showLanguageDialog = false
                }
            )
        }
        if (showLoginOutDialog) {
            LogOutSelectionDialog(
                currentState = "",
                onDismissRequest = {
                    showLoginOutDialog = false
                },
                onLogOut = {
                    onAction(SettingScreenAction.OnLogOutClick)
                }
            )
        }
        if (showDeleteAllDialog) {
            LanguageSelectionDialog(
                currentLanguage = state.currentLanguage.name,
                onDismissRequest = { showLanguageDialog = false },
                onLanguageChange = { language ->
                    onAction(SettingScreenAction.OnLanguageChange(language))
                    showLanguageDialog = false
                }
            )
        }
    }
}
