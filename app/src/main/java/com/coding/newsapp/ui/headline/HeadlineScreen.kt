package com.coding.newsapp.ui.headline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coding.domain.model.Article
import com.coding.newsapp.R
import com.coding.newsapp.ui.AppBar
import com.coding.newsapp.ui.commn.ArticleListScreen
import com.coding.newsapp.ui.commn.EmptyContent
import com.coding.newsapp.ui.commn.ShimmerEffect
import com.coding.newsapp.ui.theme.xSmallPadding
import com.coding.newsapp.ui.utils.categoryList
import org.koin.androidx.compose.koinViewModel


@Composable
fun HeadlineScreenRoot(
    paddingValues: PaddingValues,
    viewModel: HeadLineViewModel = koinViewModel(),
    onArticleClick: (Article) -> Unit,
    rootNavController: NavController,
    language: String?="ar"
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var  currentLanguage = viewModel.language
    currentLanguage=language
    HeadlineScreen(
        state = state, paddingValues = paddingValues, onAction = { action ->
            when (action) {

                is HeadLineAction.OnArticleClick -> {
                    onArticleClick(action.article)
                }
                else -> Unit
            }
            viewModel.onAction(action)

        }
        , rootNavController = rootNavController
    )


}

@Composable
fun HeadlineScreen(
    state: HeadLineState, onAction: (HeadLineAction) -> Unit, paddingValues: PaddingValues,
    rootNavController: NavController
) {
    val originDirection = LocalLayoutDirection.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = paddingValues.calculateStartPadding(originDirection),
                end = paddingValues.calculateEndPadding(originDirection),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        AppBar(appBarTitle = stringResource(R.string.headline), onClick = {
            onAction(HeadLineAction.OnSettingClick)
        })

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = xSmallPadding),
            horizontalArrangement = Arrangement.spacedBy(
                xSmallPadding, Alignment.CenterHorizontally
            )
        ) {

            items(categoryList, key = { it }) { category ->
                FilterChip(
                    selected = state.selectedCategory == category,
                    onClick = {
                        onAction(HeadLineAction.OnCategoryClick(category = category))
                    },
                    label = { CategoryTitle(category) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                )
            }

        }

        when {

            state.isLoading -> {
                ShimmerEffect()
            }

            state.newsList.isEmpty() -> {EmptyContent(
                message = stringResource(R.string.no_news),
                icon = R.drawable.ic_browse,
                isOnRetryBtnVisible = true,
                onRetryClick = {
                    if (state.selectedCategory == "Sports") {
                        onAction(HeadLineAction.OnRetryGeneralClick)
                    } else {
                        onAction(HeadLineAction.OnRetryCategoryClick)
                    }
                }
            )




            }
            else -> {
                ArticleListScreen(articleList = state.newsList,   rootNavController = rootNavController)
                }
            }
        }


    }

@Composable
fun CategoryTitle(title: String) {
    Text(text = title)
}