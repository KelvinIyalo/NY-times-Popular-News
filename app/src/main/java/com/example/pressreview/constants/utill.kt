package com.example.pressreview.constants

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.bumptech.glide.request.RequestOptions
import com.example.pressreview.R

const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "3fa0dcbb658941ceaabf42487f4aa739"
    const val PAGE_NUM = 1

fun getProgressDrawable(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri:String?,progressDrawable: CircularProgressDrawable){
    val option = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.drawable.ic_error)
            Glide.with(context)
                    .setDefaultRequestOptions(option)
                    .load(uri)
                    .into(this)
}
