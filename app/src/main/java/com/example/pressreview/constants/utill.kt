package com.example.pressreview.constants

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.bumptech.glide.request.RequestOptions
import com.example.pressreview.R

const val BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/emailed/"
    const val API_KEY = "XAFvACL5sVX8vSNQzgA5hoS9fGQa8j4J"
    const val IMAGE_PLACEHOLDER = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlnapZXOthtv-MsY7Zzx-JqeDoAvWgzfHq_AOdSHVGgVMBxi64u0SuvC2ky8o_Ua6Gq-8&usqp=CAU"
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
