package com.picodiploma.newsapp.di

import android.content.Context
import com.picodiploma.newsapp.data.NewRepository
import com.picodiploma.newsapp.data.local.room.NewsDatabase
import com.picodiploma.newsapp.data.remote.retrofit.ApiConfig

object Injection {

    fun provideRepository(context: Context): NewRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewRepository.getInstance(apiService, dao)
    }

}