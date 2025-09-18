package com.coding.data.repository




import android.util.Log
import com.coding.data.database.entity.BookMarkEntity
import com.coding.data.mappers.toArticle
import com.coding.data.mappers.toBookMarkEntity
import com.coding.data.mappers.toNewsResponse
import com.coding.data.network.RemoteNewsDataSource
import com.coding.domain.model.Article
import com.coding.domain.repository.NewsRepository
import com.coding.domain.utils.DataError
import com.coding.domain.utils.Result
import com.coding.domain.utils.map

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map


class NewsRepositoryImp(
     private val remoteNewsDataSource: RemoteNewsDataSource,
private val realmDataBase: Realm


) : NewsRepository {
    override suspend fun getHeadLineNews(category: String?,language: String?
    ): Result<List<Article>, DataError.Remote> {
        return remoteNewsDataSource.getHeadLineNews( category,language).map { it.toNewsResponse().articles }
    }

    override suspend fun getSearchNews(
        searchQuery: String,
        category: String?
        ,language: String?
    ): Result<List<Article>, DataError.Remote> {
        return remoteNewsDataSource.getSearchNews(
            country = null,
            searchQuery = searchQuery,
            language = null,
            category = category
        ).map { it.toNewsResponse().articles }
    }

   // from Local

    override suspend fun upsertArticle(article: Article) {
        realmDataBase.write { copyToRealm(article.toBookMarkEntity()
        , updatePolicy = UpdatePolicy.ALL) }
    }

    override suspend fun deleteArticle(article: Article) {
        val bookMarkEntity=realmDataBase.query<BookMarkEntity>("url == $0",article.url).first().find()
     realmDataBase.write {

         if (bookMarkEntity!= null){

             findLatest(bookMarkEntity)?.let { delete(it) }

         }
     }


    }

    override suspend fun deleteArticlesList(articles: List<Article>) {
        realmDataBase.write {
            try {
                val urls= articles.map { it.url }
                val results= realmDataBase.query<BookMarkEntity>("url IN $0",urls)
              delete(results)


            } catch (e: Exception) {
                Log.d("TAG", "deleteArticles List:${e.message} ")
            }
        }
    }
    override suspend fun deleteAllArticles() {
        realmDataBase.write {
            try {
                deleteAll()

            }catch (e: Exception){
                Log.d("TAG", "deleteAllArticles:${e.message} ")
            }
        }
    }


    override fun searchArticles(searchQuery: String): Flow<List<Article>> {
        return getArticlesAsFlow(realmDataBase, field = "title", value = searchQuery)
    }
    override  fun getAllArticles(): Flow<List<Article>> {
        return getArticlesAsFlow(realmDataBase = realmDataBase)
    }



    override fun getArticlesByCategory(category: String): Flow<List<Article>> {
        return getArticlesAsFlow(realmDataBase, field = "category", value = category)
    }

    override fun getArticlesByPriority(priority: String): Flow<List<Article>> {
        return getArticlesAsFlow(realmDataBase, field = "priority", value = priority)
    }

    override fun getArticlesByArticleUrl(url: String): Article? {
        return getArticle(realmDataBase, field = "url", value = url)?.toArticle()
    }
}



/**

Generic helper to build query as Flow.
**/

fun getArticle( realmDataBase: Realm,
                field: String? = null,
                value: String? = null): BookMarkEntity? {
    val baseQuery =
        realmDataBase.query<BookMarkEntity>("$field CONTAINS[c] $0", value)
    return baseQuery.first().find()

}
fun getArticlesAsFlow(
    realmDataBase: Realm,
    field: String? = null,
    value: String? = null
): Flow<List<Article>> {
    val baseQuery = if (field != null && value != null) {
        realmDataBase.query<BookMarkEntity>("$field CONTAINS[c] $0", value)
    } else {
        realmDataBase.query<BookMarkEntity>()
    }

    return baseQuery
        .asFlow()
        .map { results -> results.list.map { it.toArticle() } }
}