package com.project.newsapp.listener

import android.content.Context
import com.project.newsapp.data.NewsArticle
import com.project.newsapp.data.NewsModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface NewsListListener {
    interface NewsListView{
        fun loadNewsView(newsList:List<NewsArticle>)
    }
    interface NewsListPresenter{
        fun fetchNews()
        fun clearDisposable()
    }
    interface NewsDataModel{
        fun getNews():Observable<NewsModel>
    }
}