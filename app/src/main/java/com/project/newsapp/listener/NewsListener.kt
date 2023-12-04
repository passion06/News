package com.project.newsapp.listener

import com.project.newsapp.data.NewsArticleModel
import com.project.newsapp.data.NewsDetailModel
import com.project.newsapp.data.NewsModel
import io.reactivex.Observable

interface NewsListener {
    interface NewsListView{
        fun loadNewsView(newsList:List<NewsArticleModel>)
        fun handleError(message:String?)
        fun handleNewsDetailsView(newsList:NewsDetailModel)
    }
    interface NewsListPresenter{
        fun fetchNews()
        fun clearDisposable()
    }
    interface NewsDataModel{
        fun getNews(page:Int, pageSize:Int):Observable<NewsModel>
    }
}