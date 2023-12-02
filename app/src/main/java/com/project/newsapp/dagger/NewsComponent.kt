package com.project.newsapp.dagger

import com.project.newsapp.ui.NewsListActivity
import dagger.Component

@Component(modules = [NewsModule::class])
interface NewsComponent {
    fun inject(activity:NewsListActivity)
}