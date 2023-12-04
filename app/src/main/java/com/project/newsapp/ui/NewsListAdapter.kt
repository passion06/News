package com.project.newsapp.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsapp.common.NewsUtil
import com.project.newsapp.data.NewsArticleModel
import com.project.newsapp.databinding.NewsItemBinding
import com.project.newsapp.databinding.NewsItemLoadingBinding

class NewsListAdapter(private val newsItemClickListener: NewsItemClickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

     private lateinit var newsItemBinding: NewsItemBinding
     private lateinit var newsItemLoadingBinding: NewsItemLoadingBinding
     private val VIEW_TYPE_ITEM = 0
     private val VIEW_TYPE_LOADING = 1
     private var newsList = mutableListOf<NewsArticleModel>()
     private var isLoading = false
     var newsUtil = NewsUtil()
     fun addItems(newsItems :List<NewsArticleModel>){
          val startPosition = itemCount
          newsList.addAll(newsItems)
          notifyItemRangeInserted(startPosition,newsItems.size)
          isLoading = false
     }
     /** Determines whether the view type is of TYPE_ITEM or TYPE_LOADING */
     override fun getItemViewType(position: Int): Int {
          return if (newsList[position].title == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
     }

     /** inflating the appropriate layout and return the holder */
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

     /** populating the data into the view through the respective viewHolder */
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
               dateField.text = news.publishedAt?.let { newsUtil.formatDate(it) }
               holder.itemView.setOnClickListener {
                    if(news.title?.isNotEmpty() == true){
                         newsItemClickListener.onNewsRowClicked(news.title!!)
                    } else{
                         newsItemClickListener.onError("Title is empty")
                    }
               }
          } else if (holder is LoadingViewHolder) {
               val progressBar = holder.progressBar
               progressBar.visibility = View.VISIBLE
          }
     }
     /** View holder for View_TYPE_ITEM */
     inner class NewsViewHolder(itemBinding: NewsItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
          val titleTextView = itemBinding.newsItemTitle
          val newsImage = itemBinding.newsImage
          val publishedDate = itemBinding.newsDate
     }

     /** View holder for View_TYPE_LOADING */
     inner class LoadingViewHolder(loadingBinding: NewsItemLoadingBinding):RecyclerView.ViewHolder(loadingBinding.root){
          val progressBar = loadingBinding.progressBar
     }
}

interface NewsItemClickListener{
     fun onNewsRowClicked(title:String)
     fun onError(message:String)
}


