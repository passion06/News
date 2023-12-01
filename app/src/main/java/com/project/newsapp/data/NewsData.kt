package com.project.newsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(var status:String, var totalResults:Int, var articles:List<NewsArticle>):Parcelable

@Parcelize
data class NewsArticle(var title:String?, var urlToImage: String?, var publishedAt:String?) : Parcelable

@Parcelize
data class NewsDetail(var author:String?, var publishedAt: String?, var source:String?, var description:String?, var url:String?) : Parcelable