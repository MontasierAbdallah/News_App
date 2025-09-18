package com.coding.newsapp.ui.search

import com.coding.domain.model.Article


sealed interface SearchScreenAction {
    data class OnSearchQueryChange(val query: String): SearchScreenAction
    data class OnArticleClick(val article: Article): SearchScreenAction
    data class OnCategoryChange(val category: String) : SearchScreenAction
    data object OnSettingClick: SearchScreenAction
    data object OnRetryClick: SearchScreenAction
}


