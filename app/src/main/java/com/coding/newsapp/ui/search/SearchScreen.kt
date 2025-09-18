package com.coding.newsapp.ui.search


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coding.domain.model.Article

import com.coding.newsapp.R
import com.coding.newsapp.ui.AppBar
import com.coding.newsapp.ui.commn.ArticleListScreen
import com.coding.newsapp.ui.commn.EmptyContent
import com.coding.newsapp.ui.commn.ShimmerEffect

import com.coding.newsapp.ui.search.components.SearchBar
import com.coding.newsapp.ui.theme.xSmallPadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreenRoot(
    paddingValues: PaddingValues,
    viewModel: SearchViewModel= koinViewModel (),
    onArticleClick: (Article) -> Unit={},
    rootNavController: NavController,
    language: String?="ar"
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var  currentLanguage = viewModel.language
    currentLanguage=language
    SearchScreen (
        searchScreenState = state, paddingValues = paddingValues, onAction = { action ->
            when (action) {
                is SearchScreenAction.OnArticleClick -> onArticleClick(action.article)
                else -> Unit
            }
            viewModel.onAction(action)

        }
    , rootNavController = rootNavController)
}

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    searchScreenState: SearchScreenState,
    onAction: (SearchScreenAction) -> Unit
    ,
    rootNavController: NavController

) {
    val originDirection = LocalLayoutDirection.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = paddingValues.calculateStartPadding(originDirection),
                end = paddingValues.calculateEndPadding(originDirection),
                bottom = paddingValues.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.spacedBy(xSmallPadding)
    ) {
        AppBar(
            appBarTitle = stringResource(R.string.search),
            onClick = { onAction(SearchScreenAction.OnSettingClick) }
        )

        SearchBar(
            searchQuery = searchScreenState.searchQuery,
            onSearchQueryChange = { onAction(SearchScreenAction.OnSearchQueryChange(it)) },
            onSearch = { keyboardController?.hide() },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth(),
            onCategoryChange = {
                keyboardController?.hide()
                onAction(SearchScreenAction.OnCategoryChange(it))
            }
        )

        when {
            searchScreenState.isLoading && searchScreenState.searchQuery.isNotEmpty() -> {
                ShimmerEffect()
            }

            searchScreenState.errorMessage != null -> {
                Text(
                    text = searchScreenState.errorMessage.asString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            searchScreenState.searchNewsList.isEmpty() -> {
                if (searchScreenState.searchQuery.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.type_to_search),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                } else {
                    EmptyContent(
                        message = stringResource(R.string.no_search_results),
                        icon = R.drawable.ic_retry,
                        onRetryClick = { onAction(SearchScreenAction.OnRetryClick) },
                        isOnRetryBtnVisible = true
                    )
                }
            }

            else -> {
                ArticleListScreen(searchScreenState.searchNewsList, rootNavController = rootNavController  )

            }
        }
    }
}
