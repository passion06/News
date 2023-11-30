package com.project.newsapp.data

import android.content.Context
import android.util.Log
import com.project.newsapp.listener.NewsListListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsService(private val newsAPI: NewsAPI):NewsAPI,NewsListListener.NewsDataModel {

    override fun getNews(): Observable<NewsModel> {
        //Invokes the method in the NewsAPI instance triggering the network request
        return newsAPI.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable ->
                Log.d("Error in service", "${throwable.message}")
                Log.e("Error", "${throwable.message}")
            }
    }
}