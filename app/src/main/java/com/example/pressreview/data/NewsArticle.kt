package com.example.pressreview.data


import com.google.gson.annotations.SerializedName

data class NewsArticle(
    @SerializedName("articles")
    var articles: List<Article>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?
)