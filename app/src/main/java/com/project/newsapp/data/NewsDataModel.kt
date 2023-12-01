package com.project.newsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(var status:String, var totalResults:Int, var articles:List<NewsDetailModel>):Parcelable

@Parcelize
data class NewsArticleModel(var title:String?, var urlToImage: String?, var publishedAt:String?) : Parcelable

@Parcelize
data class NewsDetailModel(var source:SourceList?, var author:String?, var title:String?, var description:String?, var url:String?, var urlToImage: String?, var publishedAt: String? ) : Parcelable

@Parcelize
data class SourceList(var name:String?):Parcelable