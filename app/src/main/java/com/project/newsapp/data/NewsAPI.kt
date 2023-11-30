package com.project.newsapp.data

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NewsAPI {
    @GET("top-headlines/?country=us&category=business&apiKey=8ae186b10b84434098444186182f5e0d")
    fun getNews(): Observable<NewsModel>
    companion object{
        //create a retrofit instance for entry point
        fun create(): NewsAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            //creates a dynamic proxy that implements the NewsAPI interface and provides the class information to make
            // the actual API call
            return retrofit.create(NewsAPI::class.java)
        }
    }
}