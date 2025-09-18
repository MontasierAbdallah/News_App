package com.coding.newsapp.ui.bookmark

import com.coding.domain.model.Article



sealed interface BookMarkAction {

    data class OnArticleClick(val article: Article): BookMarkAction
    data class OnArticlesListDelete(val articles: List<Article>): BookMarkAction
    data class OnSearchQueryChange(val query: String): BookMarkAction
    data object OnSettingClick: BookMarkAction
    data object OnRetryClick: BookMarkAction
    data class OnDeleteClick(val article: Article): BookMarkAction
    data object OnSearchIconClick: BookMarkAction
    data object OnSearchClosedIconClick: BookMarkAction
}