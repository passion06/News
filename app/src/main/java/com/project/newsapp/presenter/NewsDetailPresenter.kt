package com.project.newsapp.presenter

import android.os.Bundle
import com.project.newsapp.data.NewsDetailModel

class NewsDetailPresenter(var view: NewsDetailListener.View):NewsDetailListener.Presenter {

    /* Extracts newsDetails from bundle and passes it to view */
    override fun processIntent(bundle: Bundle?) {
        val newsDetailModel:NewsDetailModel
        if(bundle!=null){
            newsDetailModel = bundle.getParcelable("newsDetails")!!
            view.displayNewsDetails(newsDetailModel)
        }else{
            throw Throwable("Empty bundle received")
        }
    }
}
interface NewsDetailListener {
    interface View{
        fun displayNewsDetails(newsDetails: NewsDetailModel)
    }
    interface Presenter{
        fun processIntent(bundle: Bundle?)
    }
}