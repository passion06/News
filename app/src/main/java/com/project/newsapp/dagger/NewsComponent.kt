package com.project.newsapp.dagger

import com.project.newsapp.presenter.NewsListPresenter
import com.project.newsapp.ui.NewsListActivity
import dagger.Component
import dagger.Module

@Component(modules = [NewsModule::class])
interface NewsComponent {
    fun provideNewsListPresenter(): NewsListPresenter
    fun inject(activity:NewsListActivity)
}