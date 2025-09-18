package com.coding.newsapp.ui.search

import com.coding.domain.model.Article

import com.coding.newsapp.ui.utils.UiText

data class SearchScreenState(
    val searchNewsList: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val selectedNewsCategory: String? = null,
    val errorMessage: UiText? = null,
    val searchQuery: String = "",
)

