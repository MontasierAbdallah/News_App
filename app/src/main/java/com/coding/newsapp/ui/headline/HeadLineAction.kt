package com.coding.newsapp.ui.headline

import com.coding.domain.model.Article

sealed interface HeadLineAction {
    data class OnArticleClick(val article: Article): HeadLineAction
    data class OnCategoryClick(val category: String): HeadLineAction
    data object OnSettingClick: HeadLineAction

    // Retry actions explicitly separated
    data object OnRetryGeneralClick: HeadLineAction   // Retry initial headlines (e.g. default category)
    data object OnRetryCategoryClick: HeadLineAction  // Retry headlines for currently selected category
}
