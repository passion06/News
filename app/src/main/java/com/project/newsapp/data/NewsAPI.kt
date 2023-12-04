package com.project.newsapp.data

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("top-headlines/?country=us&category=business&apiKey=e04b55d1a62740b78e7e5dc103bcadca")
    fun getNews(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Observable<NewsModel>

    companion object {
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