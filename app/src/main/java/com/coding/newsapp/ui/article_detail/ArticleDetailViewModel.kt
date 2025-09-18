package com.coding.newsapp.ui.article_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.domain.model.Article
import com.coding.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {



    var isBookmarked by mutableStateOf(false)

    fun isArticleBookmark(currentArticle: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            isBookmarked = newsRepository.getArticlesByArticleUrl(currentArticle.url) != null
        }
    }
    fun bookmarkArticle(currentArticle: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isBookmarked) {
                newsRepository.upsertArticle(currentArticle)
            } else {
                newsRepository.deleteArticle(currentArticle)
            }
            isBookmarked = !isBookmarked
        }
    }
}