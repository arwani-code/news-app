package com.picodiploma.newsapp.data.local.entity

import androidx.room.*

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String? = null,

    @ColumnInfo(name = "url")
    val url: String? = null,

    @ColumnInfo(name = "bookmarked")
    var isBookMarked: Boolean
)