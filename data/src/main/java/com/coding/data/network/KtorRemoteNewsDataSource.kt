package com.coding.data.network

import com.coding.data.dto.NewsResponseDto
import com.coding.data.utils.BASE_URL
import com.coding.domain.utils.DataError
import com.coding.domain.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorRemoteNewsDataSource
    (val httpClient: HttpClient): RemoteNewsDataSource {
    override suspend fun getHeadLineNews(category: String?,language:String?): Result<NewsResponseDto, DataError.Remote> {
        return safeCall <NewsResponseDto>{
            httpClient.get(
                urlString = "$BASE_URL/top-headlines"
            ){
                parameter("apiKey","b7d81f18f6b44fe783118f5333102d65")

                parameter("country","us")
                parameter("category",category)
                parameter("language","en")
                parameter("pageSize",20)
                parameter("page",1)









            }


        }
    }

    override suspend fun getNewsByCategory(
        country: String?,

        category: String?,
        language: String?
    ): Result<NewsResponseDto, DataError.Remote> {
        return safeCall<NewsResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/everything"
            ) {
                parameter("country",country)

                parameter("language",language)
                parameter("category",category)
                parameter("apiKey","b7d81f18f6b44fe783118f5333102d65")
            }
        }
    }

    override suspend fun getSearchNews(
        country: String?,
        searchQuery: String?,
        category: String?,
        language: String?
    ): Result<NewsResponseDto, DataError.Remote> {

        return safeCall<NewsResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/everything"
            ) {

                parameter("q",searchQuery)
                parameter("category",category)

                parameter("apiKey","b7d81f18f6b44fe783118f5333102d65")
            }
        }
    }

}