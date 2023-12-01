package com.project.newsapp.common

class NewsUtil() {
    /** Returns the filtered date before 'T' from publishedDate which is of format "yyyy-mm-ddT<timestamp>"
     * @param timestamp
     */
    fun formatDate(timestamp:String):String{
        val indexOfT = timestamp.indexOf('T')
        return if(indexOfT != -1){
            timestamp.substring(0,indexOfT)
        } else{
            //possible that it only contains the date
            return timestamp
        }
    }
}