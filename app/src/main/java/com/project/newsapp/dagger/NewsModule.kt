package com.project.newsapp.dagger

import com.project.newsapp.data.NewsAPI
import com.project.newsapp.data.NewsService
import com.project.newsapp.listener.NewsListener
import dagger.Module
import dagger.Provides

@Module
class NewsModule(private val view: NewsListener.NewsListView) {
    @Provides
    fun provideNewsDataModel(newsAPI: NewsAPI):NewsListener.NewsDataModel{
        return NewsService(newsAPI)
    }

    @Provides
    fun provideNewsAPI(): NewsAPI {
        return NewsAPI.create()
    }

    @Provides
    fun provideNewsListView():NewsListener.NewsListView{
        return view
    }
}