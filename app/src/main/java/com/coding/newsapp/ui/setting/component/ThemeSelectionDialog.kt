package com.coding.newsapp.ui.setting.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.coding.newsapp.R
import com.coding.newsapp.ui.theme.mediumPadding
import com.coding.newsapp.ui.theme.xLargePadding
import com.coding.newsapp.ui.theme.xSmallPadding
import com.coding.newsapp.ui.utils.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionDialog(
    onThemeChange: (Theme) -> Unit,
    currentTheme: String,
    onDismissRequest: () -> Unit
) {
    var currentSelectedTheme by rememberSaveable {
        mutableStateOf(Theme.valueOf(currentTheme))
    }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Surface(
                modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(mediumPadding)) {
                    Text(
                        text = stringResource(R.string.choose_a_theme),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(xSmallPadding)
                    )

                    Theme.entries.forEach { theme ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { currentSelectedTheme = theme },
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentSelectedTheme == theme,
                                onClick = { currentSelectedTheme = theme },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(text = stringResource(theme.titleRes))
                        }
                    }

                    Spacer(modifier = Modifier.height(xLargePadding))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismissRequest) {
                            Text(text = stringResource(R.string.cancel))
                        }
                        Spacer(modifier = Modifier.width(mediumPadding))
                        TextButton(onClick = { onThemeChange(currentSelectedTheme) }) {
                            Text(text = stringResource(R.string.apply))
                        }
                    }
                }
            }
        }
    )
}
