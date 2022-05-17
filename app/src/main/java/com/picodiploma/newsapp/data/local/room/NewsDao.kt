package com.picodiploma.newsapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.picodiploma.newsapp.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    fun getNews(): LiveData<List<NewsEntity>>

    @Query("SELECT * FROM news where bookmarked = 1")
    fun getBookMarkedNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Update
    suspend fun update(news: NewsEntity)

    @Query("DELETE FROM news WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM news WHERE title = :title AND bookmarked = 1)")
    suspend fun isBookMarked(title: String): Boolean
}