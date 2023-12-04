package com.project.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.newsapp.R
import com.project.newsapp.common.NewsUtil
import com.project.newsapp.dagger.DaggerNewsComponent
import com.project.newsapp.dagger.NewsModule

import com.project.newsapp.data.NewsArticleModel
import com.project.newsapp.data.NewsDetailModel
import com.project.newsapp.databinding.NewsListBinding
import com.project.newsapp.listener.NewsListener
import com.project.newsapp.presenter.NewsListPresenter
import javax.inject.Inject


class NewsListActivity : AppCompatActivity(), NewsListener.NewsListView {
    @Inject
    lateinit var newsListPresenter: NewsListPresenter
    private lateinit var newsListBinding: NewsListBinding
    private lateinit var newsListRecyclerView: RecyclerView
    private lateinit var newsListAdapter: NewsListAdapter
    private val newsUtil = NewsUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsListBinding = NewsListBinding.inflate(layoutInflater)
        setContentView(newsListBinding.root)
        DaggerNewsComponent.builder()
            .newsModule(NewsModule(this))
            .build()
            .inject(this)
        configureView()
    }

    /* Call back method implementation on news row click */
    private val newsItemClickListener = object : NewsItemClickListener {
        override fun onNewsRowClicked(title: String) {
            Log.d("NewsListActivity","Clicked on item with title:$title")
            newsListPresenter.fetchNewsDetails(baseContext, title)
        }

        override fun onError(message: String) {
           newsUtil.displayError(baseContext,getString(R.string.title_for_the_newsitem_is_null))
        }
    }

    private fun configureView() {
        newsListPresenter.fetchNews()
        newsListRecyclerView = newsListBinding.newsRecyclerview
        newsListRecyclerView.layoutManager = LinearLayoutManager(this)
        newsListAdapter = NewsListAdapter(newsItemClickListener)
        newsListRecyclerView.adapter = newsListAdapter
    }

    /** This method handles rendering newsList in the adapter and uses scrollListener for pagination functionality
     * @param newsList
     */
    override fun loadNewsView(newsList: List<NewsArticleModel>) {
        newsListAdapter.addItems(newsList)
        newsListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                newsListPresenter.updateLastVisibleItemPosition(lastVisibleItem)
                val totalItemCount = newsListAdapter.itemCount
                //If scroll position reaches bottom of the list
                if (lastVisibleItem == totalItemCount - 1) {
                    newsListPresenter.loadMoreNews()
                }
            }
        })
        newsListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun handleError(message: String?) {
        if (message != null) {
            newsUtil.displayError(this, message)
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    /* Passes Intent with newsDetails to the NewsDetailActivity */
    override fun handleNewsDetailsView(newsList: NewsDetailModel) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra(getString(R.string.newsdetails), newsList)
        startActivity(intent)
    }

    /* handles clearing disposables when activity is destroyed */
    override fun onDestroy() {
        super.onDestroy()
        newsListPresenter.clearDisposable()
    }
}

