package com.project.newsapp.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsapp.R
import com.project.newsapp.data.NewsArticle
import com.project.newsapp.databinding.NewsItemBinding

class NewsListAdapter(private var newsList: List<NewsArticle>):RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>(){

     private lateinit var newsItemBinding: NewsItemBinding
     //inflating the layout and return the holder
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
          newsItemBinding = NewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
          return NewsViewHolder(newsItemBinding)
     }
     override fun getItemCount(): Int {
          return newsList.size
     }
     //populating the data into the item through the holder
     override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
          val news  = newsList[position]
          val title = holder.titleTextView
          title.text = news.title
          val newsImage = holder.newsImage
          if(news.urlToImage!=null) {
               Glide.with(holder.itemView.context).load(news.urlToImage).into(newsImage)
               newsImage.setImageURI(Uri.parse(news.urlToImage))
          } else {
               newsImage.visibility = View.GONE
          }
          val dateField = holder.publishedDate
          dateField.text = news.publishedAt
     }
     //assign updated news to newsList
     fun updateNewsAdapter(updatedNewsList:List<NewsArticle>){
          this.newsList = updatedNewsList
          notifyDataSetChanged()
     }

     inner class NewsViewHolder(itemBinding: NewsItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
          val titleTextView: TextView = itemBinding.newsItemTitle
          val newsImage: ImageView = itemBinding.newsImage
          val publishedDate : TextView = itemBinding.newsDate
     }
}


