package com.project.newsapp.ui
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    @Inject
    lateinit var newsListPresenter: NewsListPresenter
    private lateinit var newsListBinding:NewsListBinding
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsListBinding = NewsListBinding.inflate(layoutInflater)
        setContentView(newsListBinding.root)
        //DaggerApp.newsComponent.inject(this)
        val newsAPI = NewsAPI.create()
        val newsService = NewsService(newsAPI)
        newsListPresenter = NewsListPresenter(this,newsService,applicationContext)
      //  newsListPresenter = DaggerApp.newsComponent.provideNewsListPresenter()
        configureView()
        }

    private fun configureView(){
        newsListPresenter.fetchNews()
    }

    override fun loadNewsView(newsList:List<NewsArticle>) {
        val newsListRecyclerView = newsListBinding.newsRecyclerview
        newsListRecyclerView.layoutManager = LinearLayoutManager(this)
        newsListRecyclerView.apply {
            val newsListAdapter = NewsListAdapter()
            adapter = newsListAdapter
            newsListAdapter.addItems(newsList)
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    if(!isLoading){
                        //If scroll position reaches bottom of the list
                        if(layoutManager!=null && layoutManager.findLastCompletelyVisibleItemPosition()==newsList.size-1){
                            newsListAdapter.addItems(newsList)
                            isLoading = true
                        }
                    }
                }
            })
            addItemDecoration(DividerItemDecoration(baseContext,DividerItemDecoration.VERTICAL))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        newsListPresenter.clearDisposable()
    }
}

