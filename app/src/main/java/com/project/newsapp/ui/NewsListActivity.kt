package com.project.newsapp.ui
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.newsapp.dagger.DaggerApp
import com.project.newsapp.dagger.DaggerNewsComponent
import com.project.newsapp.data.NewsAPI

import com.project.newsapp.data.NewsArticle
import com.project.newsapp.data.NewsService
import com.project.newsapp.databinding.NewsListBinding
import com.project.newsapp.listener.NewsListListener
import com.project.newsapp.presenter.NewsListPresenter
import javax.inject.Inject


class NewsListActivity : AppCompatActivity(),NewsListListener.NewsListView {
   // @Inject
    lateinit var newsListPresenter: NewsListPresenter
    private lateinit var newsListBinding:NewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsListBinding = NewsListBinding.inflate(layoutInflater)
        setContentView(newsListBinding.root)
        val newsAPI = NewsAPI.create()
        val newsService = NewsService(newsAPI)
       // DaggerApp.newsComponent.inject(this)
        newsListPresenter = NewsListPresenter(this,newsService,applicationContext)
       // newsListPresenter = DaggerApp.newsComponent.provideNewsListPresenter()
        configureView()
        }

    private fun configureView(){
        newsListPresenter.fetchNews()
    }

    override fun loadNewsView(newsList:List<NewsArticle>) {
        val newsListRecyclerView = newsListBinding.newsRecyclerview
        val newsLayoutManager = LinearLayoutManager(this)
        newsListRecyclerView.apply {
            layoutManager = newsLayoutManager
            val newsListAdapter = NewsListAdapter(newsList)
            adapter = newsListAdapter
            addItemDecoration(DividerItemDecoration(baseContext,DividerItemDecoration.VERTICAL))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        newsListPresenter.clearDisposable()
    }
}

