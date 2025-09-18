package com.coding.newsapp.ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coding.domain.model.Article
import com.coding.newsapp.ui.article_detail.ArticleDetailScreen
import com.coding.newsapp.ui.navigation.Route

import com.coding.newsapp.ui.article_detail.ArticleDetailViewModel
import com.coding.newsapp.ui.bookmark.BookMarkScreenRoot
import com.coding.newsapp.ui.bookmark.BookMarkViewModel
import com.coding.newsapp.ui.headline.HeadLineViewModel
import com.coding.newsapp.ui.headline.HeadlineScreenRoot
import com.coding.newsapp.ui.search.SearchScreenRoot
import com.coding.newsapp.ui.search.SearchViewModel
import com.coding.newsapp.ui.setting.SettingScreenRoot
import com.coding.newsapp.ui.setting.SettingViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel


@Composable
fun NavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues,
    settingViewModel: SettingViewModel
) {
    val currentLanguage = remember {
        settingViewModel.state.value.currentLanguage.name
    }
    NavHost(
        navController = rootNavController,
        startDestination = Route.Headline,
    ) {
        composable<Route.Headline> {
            val viewModel: HeadLineViewModel = koinViewModel()
            HeadlineScreenRoot(

                language = currentLanguage,
                    paddingValues = innerPadding,
            viewModel = viewModel,
            onArticleClick = { _: Article -> /* navigate to detail later */ },
            rootNavController = rootNavController
            )

        }
        composable<Route.Bookmark> {
            val bookMarkViewModel: BookMarkViewModel = koinViewModel ()
            BookMarkScreenRoot(
                viewModel = bookMarkViewModel,
                onArticleClick = {},
                paddingValues = innerPadding,
                rootNavController = rootNavController
            )
        }
        composable<Route.Search> {
            val searchViewModel: SearchViewModel = koinViewModel ()
            SearchScreenRoot(
                language = currentLanguage,
                viewModel = searchViewModel,
                onArticleClick = {},
                paddingValues = innerPadding,
                rootNavController = rootNavController
            )
        }

        composable<Route.NewsDetail> {
           val  viewModel: ArticleDetailViewModel = koinViewModel()
            rootNavController.previousBackStackEntry?.savedStateHandle?.get<String>("article")?.let { article ->
                val currentArticle: Article = Json.decodeFromString(article)
                ArticleDetailScreen(rootNavController, currentArticle,viewModel)
            }
        }
        composable<Route.SettingDetail> {

            SettingScreenRoot(
                viewModel = settingViewModel
            )
        }
    }
}