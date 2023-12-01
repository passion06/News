package com.project.newsapp.dagger

import android.app.Application
import android.content.Context
import com.project.newsapp.data.NewsAPI
import com.project.newsapp.data.NewsService
import com.project.newsapp.listener.NewsListener
import com.project.newsapp.presenter.NewsListPresenter
import com.project.newsapp.ui.NewsListActivity
import dagger.Module
import dagger.Provides

@Module
class NewsModule(private val application:Application) {
    @Provides
    fun provideNewsDataModel(newsAPI: NewsAPI):NewsListener.NewsDataModel{
        return NewsService(newsAPI)
    }

    @Provides
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }

    @Provides
    fun provideNewsListActivity():NewsListener.NewsListView{
        return NewsListActivity()
    }

    @Provides
    fun provideNewsAPI(): NewsAPI {
        return NewsAPI.create()
    }

    @Provides
    fun provideNewsListPresenter(view:NewsListener.NewsListView, context:Context, dataModel:NewsListener.NewsDataModel): NewsListener.NewsListPresenter{
        return NewsListPresenter(view,dataModel,context)
    }
}