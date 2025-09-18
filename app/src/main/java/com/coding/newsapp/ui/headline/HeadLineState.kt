package com.coding.newsapp.ui.headline

import com.coding.domain.model.Article

import com.coding.newsapp.ui.utils.UiText


data class HeadLineState(
    val selectedCategory: String = "Sports",
    val newsList: List<Article> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: UiText? = null,

)

