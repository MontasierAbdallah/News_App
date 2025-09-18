package com.coding.newsapp.ui.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import com.coding.newsapp.ui.theme.mediumPadding
import com.coding.newsapp.ui.theme.xLargePadding

@Composable
fun SettingItem(
    onItemClick: () -> Unit,
    itemPainter: Painter,
    itemName: String,
    itemColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(mediumPadding),
        horizontalArrangement = Arrangement.spacedBy(mediumPadding, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = itemPainter,
            contentDescription = itemName,
            modifier = Modifier.size(xLargePadding),
            colorFilter = ColorFilter.tint(color = itemColor)
        )
        Text(
            text = itemName,
            color = itemColor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal
        )
    }
}
