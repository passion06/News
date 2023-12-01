package com.project.newsapp.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsapp.data.NewsArticle
import com.project.newsapp.databinding.NewsItemBinding
import com.project.newsapp.databinding.NewsItemLoadingBinding

class NewsListAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>(){

     private lateinit var newsItemBinding: NewsItemBinding
     private lateinit var newsItemLoadingBinding: NewsItemLoadingBinding
     private val VIEW_TYPE_ITEM = 0
     private val VIEW_TYPE_LOADING = 1
     private var newsList = mutableListOf<NewsArticle>()
     private var isLoading = false
     fun addItems(newsItems :List<NewsArticle>){
          val startPosition = itemCount
          newsList.addAll(newsItems)
          notifyItemRangeInserted(startPosition,newsItems.size)
          isLoading = false
     }
     //To determine whether the view type is of TYPE_ITEM or TYPE_LOADING
     override fun getItemViewType(position: Int): Int {
          return if (newsList[position].title == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
     }

     //inflating the layout and return the holder
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
          return if(viewType == VIEW_TYPE_LOADING){
               newsItemLoadingBinding = NewsItemLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
              LoadingViewHolder(newsItemLoadingBinding)
          } else {
               newsItemBinding =
                    NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
               NewsViewHolder(newsItemBinding)
          }
     }
     override fun getItemCount(): Int {
          return if (newsList == null) 0 else newsList.size
     }

     //populating the data into the item through the holder
     override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
          if (holder is NewsViewHolder) {
               val news = newsList[position]
               val title = holder.titleTextView
               title.text = news.title
               val newsImage = holder.newsImage
               if (news.urlToImage != null) {
                    Glide.with(holder.itemView.context).load(news.urlToImage).into(newsImage)
                    newsImage.setImageURI(Uri.parse(news.urlToImage))
               } else {
                    newsImage.visibility = View.GONE
               }
               val dateField = holder.publishedDate
               dateField.text = news.publishedAt
          } else if (holder is LoadingViewHolder) {
               val progressBar = holder.progressBar
               progressBar.visibility = View.VISIBLE
          }
     }

     inner class NewsViewHolder(itemBinding: NewsItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
          val titleTextView: TextView = itemBinding.newsItemTitle
          val newsImage: ImageView = itemBinding.newsImage
          val publishedDate : TextView = itemBinding.newsDate
     }

     inner class LoadingViewHolder(loadingBinding: NewsItemLoadingBinding):RecyclerView.ViewHolder(loadingBinding.root){
          val progressBar:ProgressBar = loadingBinding.progressBar
     }
}


