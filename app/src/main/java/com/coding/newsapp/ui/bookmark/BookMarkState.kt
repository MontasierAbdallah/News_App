package com.coding.newsapp.ui.bookmark

import com.coding.domain.model.Article


data class BookMarkState(
    val isLoading: Boolean= true,
    val isSearchBarVisible: Boolean= false,
    val bookMarList: List<Article> = emptyList(),
    val searchNewsList: List<Article> = emptyList(),
    val errorMessage: String? = null,
    var category: String? ="General",
    var priority: String? ="NORMAL",
    val searchQuery: String = "",

)
