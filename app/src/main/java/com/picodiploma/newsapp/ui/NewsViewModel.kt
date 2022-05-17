package com.picodiploma.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picodiploma.newsapp.data.NewRepository
import com.picodiploma.newsapp.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class NewsViewModel(private val newRepository: NewRepository) : ViewModel() {

    fun getHeadlineNews() = newRepository.getHeadlineNews()

    fun getBookmarkedNews() = newRepository.getBookmarkedNews()

    fun saveNews(news: NewsEntity) = viewModelScope.launch {
        newRepository.setBookmarkedNews(news, true)
    }

    fun deleteNews(news: NewsEntity) = viewModelScope.launch {
        newRepository.setBookmarkedNews(news, false)
    }
}