package com.coding.domain.model

data class Article(


    val author: String? = null,

    val content: String?,

    val description: String?,


    val publishedAt: String,

    val source: Source? =null,

    val title: String,

    val url: String,

    val urlToImage: String?,
    var category: String? ="",
    var priority: String? =""
)
