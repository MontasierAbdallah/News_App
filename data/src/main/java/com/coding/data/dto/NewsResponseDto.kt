package com.coding.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseDto(
    @SerialName("articles")
    val articles: List<ArticleDto> = emptyList(),
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int=0
)