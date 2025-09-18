package com.coding.domain.repository

import com.coding.domain.model.Article
import com.coding.domain.utils.DataError
import com.coding.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getHeadLineNews(
        category: String?="business",language: String?="ar"

    ): Result<List<Article>, DataError.Remote>

    suspend fun getSearchNews(
        searchQuery: String, category: String?,language: String?="ar"
    ): Result<List<Article>, DataError.Remote>



suspend fun deleteArticle(article: Article)
suspend fun deleteArticlesList(articles: List<Article>)
 fun getArticlesByCategory(category: String):Flow<List<Article>>
 fun getArticlesByPriority(priority: String):Flow<List<Article>>
fun getArticlesByArticleUrl(url: String): Article?
fun getAllArticles(): Flow<List<Article>>
suspend fun deleteAllArticles()
 fun searchArticles(searchQuery: String): Flow<List<Article>>


    suspend fun upsertArticle(article: Article)
}