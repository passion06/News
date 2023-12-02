package com.project.newsapp.common

import android.content.Context
import androidx.appcompat.app.AlertDialog

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

    /** Displays an AlertDialog with the error message in the view
     * @param context,message
     */
    fun displayError(context: Context, message:String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK"){ dialog,_->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}