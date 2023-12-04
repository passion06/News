package com.project.newsapp.presenter

import android.content.Context
import android.util.Log
import com.project.newsapp.data.NewsArticleModel
import com.project.newsapp.data.NewsDetailModel
import com.project.newsapp.listener.NewsListener
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class NewsListPresenter @Inject constructor(
    private val view: NewsListener.NewsListView,
    private val dataModel: NewsListener.NewsDataModel
) : NewsListener.NewsListPresenter {
    private lateinit var disposable: Disposable
    private var currentPage = 1
    private var pageSize = 8
    private var newsList = mutableListOf<NewsArticleModel>()
    private var isLoading = false
    private var lastVisibleItemPosition = 1

    /** Retrieves news data from news service and maps it to NewsArticleModel */
    override fun fetchNews() {
        if (currentPage == 1) {
            newsList.clear()
            isLoading = true
        }
        disposable = dataModel.getNews(currentPage, pageSize)
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
                    handleNewsListView(newsList)
                },

                { error ->
                    isLoading = false
                    Log.e("NewsListPresenter", "Error fetching news list: ${error.message}")
                }
            )
    }

    fun loadMoreNews() {
        //reset page size to limit again
        pageSize = 8
        //Increment current page to load the next news page
        currentPage++
        val startIndex = (currentPage - 1) * pageSize
        val endIndex = startIndex + pageSize
        //If fetching articles by pageSize will cause it to go out of bounds
        if (endIndex > newsList.size) {
            pageSize = endIndex - newsList.size
            fetchNews()
        } else {
            fetchNews()
        }
    }

    /** To keep track of the last visible item position */
    fun updateLastVisibleItemPosition(position: Int) {
        // If backward scrolling detected, decrement currentPage
        if (position < lastVisibleItemPosition) {
            currentPage = maxOf(1, currentPage - 1)
        }
        lastVisibleItemPosition = position
    }

    /** Passes newsData from success response to the view */
    private fun handleNewsListView(newsData: List<NewsArticleModel>) {
        if (newsData.isNotEmpty()) {
            newsList.addAll(newsData)
            view.loadNewsView(newsData)
        } else {
            view.handleError("Error fetching news list")
        }
        isLoading = false
    }

    /** Retrieves news data from news service and maps it to NewsDetailModel */
    fun fetchNewsDetails(context: Context, selectedNewsTitle: String?) {
        disposable = dataModel.getNews(currentPage, pageSize)
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
                    Log.d("All newsDetails","${newsDetailsList.map{it.title}}")
                    val filteredNewsDetailList =
                        newsDetailsList.filter { it.title.equals(selectedNewsTitle,ignoreCase = true) }
                    Log.d("Filtered News Details","${filteredNewsDetailList.map{it.title}}")
                    handleNewsDetails(context, filteredNewsDetailList.firstOrNull())
                },

                { error ->
                    Log.e("NewsListPresenter", "Error fetching news details: ${error.message}")
                }
            )
    }

    /** Passes newsDetails data from success response to the view */
    private fun handleNewsDetails(context: Context, newsDetail: NewsDetailModel?) {
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