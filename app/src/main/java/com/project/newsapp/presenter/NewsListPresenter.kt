package com.project.newsapp.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.project.newsapp.data.NewsArticleModel
import com.project.newsapp.data.NewsDetailModel
import com.project.newsapp.listener.NewsListener
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class NewsListPresenter@Inject constructor(private val view: NewsListener.NewsListView, private val dataModel: NewsListener.NewsDataModel):NewsListener.NewsListPresenter {
    private lateinit var disposable: Disposable
    override fun fetchNews() {
                disposable = dataModel.getNews()
                    .map { response ->
                        response.articles.map { newsData ->
                            NewsArticleModel(
                                newsData.title,
                                newsData.urlToImage,
                                newsData.publishedAt
                            )
                        }
                    }
                        .subscribe(
                            { newsList ->
                                Log.d("API Response", newsList.toString())
                                handleNewsListView(newsList)
                            },

                            { error ->
                                Log.e("NewsListPresenter","Error fetching news list: ${error.message}")
                            }
                        )
    }

    private fun handleNewsListView(newsData : List<NewsArticleModel>){
        if(newsData.isNotEmpty()) {
            view.loadNewsView(newsData)
        } else{
            view.handleError("Error fetching news list")
        }
    }

     fun fetchNewsDetails(context: Context, selectedNewsTitle:String?){
         disposable = dataModel.getNews()
             .map { response ->
                 response.articles.map { newsData ->
                     NewsDetailModel(
                         newsData.source,
                         newsData.author,
                         newsData.title,
                         newsData.description,
                         newsData.url,
                         newsData.urlToImage,
                         newsData.publishedAt
                     )
                 }
             }
             .subscribe(
                 { newsDetailsList ->
                     val filteredNewsDetailList = newsDetailsList.filter{it.title == selectedNewsTitle}
                     Log.d("API Response", filteredNewsDetailList.toString())
                     handleNewsDetails(context,filteredNewsDetailList.firstOrNull())
                 },

                 { error ->
                     Log.e("NewsListPresenter","Error fetching news details: ${error.message}")
                 }
             )
    }

    private fun handleNewsDetails(context:Context,newsDetail:NewsDetailModel?){
        if (newsDetail != null) {
            view.handleNewsDetailsView(newsDetail)
        } else {
            view.handleError("Error fetching news details")
        }
    }

    override fun clearDisposable() {
       disposable.dispose()
    }

}