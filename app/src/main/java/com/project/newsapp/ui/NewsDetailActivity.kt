package com.project.newsapp.ui

import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.newsapp.R
import com.project.newsapp.common.NewsUtil
import com.project.newsapp.data.NewsDetailModel
import com.project.newsapp.databinding.NewsDetailBinding
import com.project.newsapp.presenter.NewsDetailListener
import com.project.newsapp.presenter.NewsDetailPresenter

class NewsDetailActivity:AppCompatActivity(), NewsDetailListener.View {

    private lateinit var newsDetailBinding:NewsDetailBinding
    private lateinit var newsDetailPresenter: NewsDetailPresenter

    private lateinit var newsAuthorText: TextView
    private lateinit var newsPublicationDateText: TextView
    private lateinit var newsSourceText: TextView
    private lateinit var newsImageView: ImageView
    private lateinit var newsTitleText: TextView
    private lateinit var newsDescriptionText: TextView
    private lateinit var newsLinkText: TextView
    var newsUtil = NewsUtil()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsDetailBinding = NewsDetailBinding.inflate(layoutInflater)
        setContentView(newsDetailBinding.root)
        newsDetailPresenter = NewsDetailPresenter(this)
        configureView()
        val bundle = intent.extras
        newsDetailPresenter.processIntent(bundle)
    }

    private fun configureView(){
        newsDetailBinding.apply{
            newsTitleText = newsTitle
            newsImageView = newsImage
            newsAuthorText = newsAuthor
            newsPublicationDateText = newsPublicationDate
            newsSourceText = newsSource
            newsDescriptionText = newsDescription
            newsLinkText = newsArticleLink
        }
    }

    override fun displayNewsDetails(newsDetails: NewsDetailModel){
        newsDetails.apply{
            if (newsDetails.urlToImage != null) {
                Glide.with(newsImageView.context).load(newsDetails.urlToImage).into(newsImageView)
                newsImageView.setImageURI(Uri.parse(newsDetails.urlToImage))
            } else {
                newsImageView.visibility = View.GONE
            }
            newsTitleText.text = title
            newsAuthorText.text = author
            newsSourceText.text = addToLabel(getString(R.string.source_label), source?.name)
            newsDescriptionText.text = description
            newsPublicationDateText.text = publishedAt?.let { newsUtil.formatDate(it) }
            newsLinkText.text = url
            newsLinkText.movementMethod = LinkMovementMethod.getInstance()
        }
    }
    private fun addToLabel(label: String, value: String?) =
        if (!value.isNullOrBlank()) "$label $value" else "$label No data available"


}