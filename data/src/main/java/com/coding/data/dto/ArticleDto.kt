package com.coding.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class ArticleDto(
    @SerialName("author")
    val author: String? = null,
    @SerialName("content")
    val content: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("publishedAt")


    val publishedAt: String,
    @SerialName("source")
    val sourceDto: SourceDto,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
    @SerialName("urlToImage")
    val urlToImage: String?
)