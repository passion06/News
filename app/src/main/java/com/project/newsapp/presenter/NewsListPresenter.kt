package com.project.newsapp.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.project.newsapp.data.NewsArticle
import com.project.newsapp.data.NewsService
import com.project.newsapp.listener.NewsListListener
import com.project.newsapp.ui.NewsListActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListPresenter@Inject constructor(private val view: NewsListListener.NewsListView, private val dataModel: NewsListListener.NewsDataModel, private val context: Context):NewsListListener.NewsListPresenter {
    private lateinit var disposable: Disposable
    override fun fetchNews() {
                disposable = dataModel.getNews()
                    .map { response ->
                        response.articles.map { newsData ->
                            NewsArticle(
                                newsData.title,
                                newsData.urlToImage,
                                newsData.publishedAt
                            )
                        }
                    }
                        .subscribe(
                            { newsList ->
                                Log.d("API Response", newsList.toString())
                                handleNewsArticleView(newsList)
                            },

                            { error ->
                                Log.e("NewsListPresenter","Error fetching news list: ${error.message}")
                            }
                        )
    }

    private fun handleNewsArticleView(newsData : List<NewsArticle>){
        if(newsData.isNotEmpty()) {
            view.loadNewsView(newsData)
        } else{
            Toast.makeText(context, "Problem loading News", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun clearDisposable() {
       disposable.dispose()
    }

}