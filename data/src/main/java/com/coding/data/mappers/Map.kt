package com.coding.data.mappers


import com.coding.data.database.entity.BookMarkEntity
import com.coding.data.dto.ArticleDto
import com.coding.data.dto.NewsResponseDto
import com.coding.data.dto.SourceDto
import com.coding.domain.model.Article
import com.coding.domain.model.NewsResponse
import com.coding.domain.model.Source


fun NewsResponseDto.toNewsResponse(): NewsResponse{
    return NewsResponse(
        articles = this.articles.map { it.toArticle() },
        status=this.status,
        totalResults= this.totalResults
    )
}


fun ArticleDto.toArticle(): Article{
    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.sourceDto.toSource(),
        title = this.title,
        url =this.url,
        urlToImage = this.urlToImage
    )

}
fun SourceDto.toSource(): Source{
return Source(
id = this.id,
name = this.name

)}
fun Article.toBookMarkEntity(): BookMarkEntity{
    val bookMarkEntity= BookMarkEntity()
    bookMarkEntity.url=this.url
    bookMarkEntity.author = this.author
    bookMarkEntity.content = this.content
    bookMarkEntity.description = this.description
    bookMarkEntity.publishedAt = this.publishedAt
    bookMarkEntity.title = this.title

    bookMarkEntity.urlToImage = this.urlToImage

    bookMarkEntity.category = this.category
    bookMarkEntity.priority = this.priority
   return bookMarkEntity
}




fun BookMarkEntity.toArticle(): Article{
    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage,
        source = null,
        category = this.category,
        priority = this.priority
    )
}



