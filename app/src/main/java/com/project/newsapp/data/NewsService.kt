package com.project.newsapp.data

import android.util.Log
import com.project.newsapp.listener.NewsListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsService(private val newsAPI: NewsAPI):NewsAPI,NewsListener.NewsDataModel {

    /* Invokes the method in the NewsAPI instance triggering the network request */
    override fun getNews(): Observable<NewsModel> {
        return newsAPI.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable ->
                Log.e("Error", "${throwable.message}")
            }
    }
}