package com.coding.data.network

import com.coding.data.dto.NewsResponseDto
import com.coding.domain.utils.DataError
import com.coding.domain.utils.Result

interface RemoteNewsDataSource {

    suspend fun getHeadLineNews(
        category: String?,
        language: String?,
    ): Result<NewsResponseDto, DataError.Remote>

    suspend fun getNewsByCategory(
        country:String?,

        category: String?,
        language: String?
    ): Result<NewsResponseDto, DataError.Remote>


    suspend fun getSearchNews(
        country:String?,
        searchQuery:String?,
        category: String?,
        language: String?
    ): Result<NewsResponseDto, DataError.Remote>

}