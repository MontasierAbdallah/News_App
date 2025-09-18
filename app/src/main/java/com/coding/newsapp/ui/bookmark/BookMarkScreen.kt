package com.coding.newsapp.ui.bookmark


import androidx.compose.animation.Crossfade
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
import com.coding.newsapp.ui.bookmark.components.SearchBar
import com.coding.newsapp.ui.commn.ArticleListScreen
import com.coding.newsapp.ui.commn.EmptyContent
import com.coding.newsapp.ui.commn.ShimmerEffect



import org.koin.androidx.compose.koinViewModel


@Composable
fun BookMarkScreenRoot(

    viewModel: BookMarkViewModel = koinViewModel(),
    paddingValues: PaddingValues,
    onArticleClick: (Article) -> Unit,
    rootNavController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookMarkScreen(
        paddingValues = paddingValues,
        state = state,
        onAction = { action ->
            when (action) {
                is BookMarkAction.OnArticleClick -> onArticleClick(action.article)

                else -> Unit
            }
            viewModel.onAction(action)
        }
        ,rootNavController=rootNavController
    )
}

@Composable
fun BookMarkScreen(
    paddingValues: PaddingValues,
    state: BookMarkState,
    onAction: (BookMarkAction) -> Unit,
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
            )
    ) {
        BookMarkAppBar(
            appBarTitle = stringResource(R.string.bookmark),
            onSettingClick = { onAction(BookMarkAction.OnSettingClick) },
            onSearchClick = {onAction(BookMarkAction.OnSearchIconClick)},
            onFilterListClick = {}
        )

        Column {
            Crossfade(targetState = state.isSearchBarVisible) {isVisible->
                if(isVisible){
                    SearchBar(
                        searchQuery = state.searchQuery,
                        onSearchQueryChange = { onAction(BookMarkAction.OnSearchQueryChange(it))},
                        onSearch = { keyboardController?.hide()

                            onAction(BookMarkAction.OnSearchClosedIconClick)
                        },
                        modifier = Modifier
                            .widthIn(max = 400.dp)
                            .fillMaxWidth(),
                        onClosedClick = {
                            onAction(BookMarkAction.OnSearchClosedIconClick)
                        }
                    )
                    when {
                        state.isLoading && state.searchQuery.isNotEmpty() -> {
                            ShimmerEffect()
                        }

                        state.errorMessage != null -> {
                            Text(
                                text = state.errorMessage,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        state.searchNewsList.isEmpty() -> {
                            if (state.searchQuery.isEmpty()) {
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
                                    onRetryClick = { onAction(BookMarkAction.OnRetryClick) },
                                    isOnRetryBtnVisible = true
                                )
                            }
                        }

                        else -> {
                            ArticleListScreen(state.searchNewsList
                            ,   rootNavController = rootNavController)
                        }
                    }
                }else{
                    when {
                        state.isLoading  -> {
                            ShimmerEffect()
                        }

                        state.errorMessage != null -> {
                            Text(
                                text = state.errorMessage,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        state.bookMarList.isEmpty() -> {


                            EmptyContent(
                                message = stringResource(R.string.no_bookmarks),
                                icon = R.drawable.ic_retry,
                                onRetryClick = { onAction(BookMarkAction.OnRetryClick) },
                                isOnRetryBtnVisible = true
                            )
                        }


                        else -> {
                            ArticleListScreen(state.searchNewsList,
                                rootNavController = rootNavController)
                        }
                    }
                }

            }
        }





            }
        }






































