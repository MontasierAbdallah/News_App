package com.coding.newsapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.coding.meet.newsapp.theme.smallPadding
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NavigationSideBar(
    items: List<NavigationItem>,
    currentRoute: String?,
    onItemClick: (NavigationItem) -> Unit
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        items.forEach { navigationItem ->
            val isSelected = navigationItem.route::class.qualifiedName == currentRoute

            NavigationRailItem(
                modifier = Modifier.padding(vertical = smallPadding),
                icon = {
                    Icon(
                        painter = painterResource(navigationItem.icon),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(navigationItem.title),
                        style = if (isSelected) MaterialTheme.typography.labelLarge
                        else MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = isSelected,
                onClick = { onItemClick(navigationItem) }
            )
        }
    }
}
