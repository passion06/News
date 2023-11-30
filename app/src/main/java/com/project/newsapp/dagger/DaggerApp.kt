package com.project.newsapp.dagger

import android.app.Application

open class DaggerApp : Application() {
    companion object {
        lateinit var newsComponent: NewsComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        newsComponent = DaggerNewsComponent.builder()
            .newsModule(NewsModule(this))
            .build()
    }
}
