package com.picodiploma.newsapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.picodiploma.newsapp.data.local.entity.NewsEntity
import com.picodiploma.newsapp.data.local.room.NewsDao
import com.picodiploma.newsapp.data.remote.retrofit.ApiService

class NewRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews("db874e166f4c473e9132d19a45135274")
            val articles = response.articles
            val newsList = articles.map { article ->
                val isBookmarked = newsDao.isBookMarked(article.title)
                NewsEntity(
                    article.title,
                    article.publishedAt,
                    article.urlToImage,
                    article.url,
                    isBookmarked
                )
            }
            newsDao.deleteAll()
            newsDao.insertNews(newsList)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<NewsEntity>>> = newsDao.getNews().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> = newsDao.getBookMarkedNews()

    suspend fun setBookmarkedNews(news: NewsEntity, bookmarkState: Boolean){
            news.isBookMarked = bookmarkState
            newsDao.update(news)
    }

    companion object {
        @Volatile
        private var instance: NewRepository? = null

        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ): NewRepository = instance ?: synchronized(this) {
            instance ?: NewRepository(apiService, newsDao)
        }.also { instance = it }
    }
}