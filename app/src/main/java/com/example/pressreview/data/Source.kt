package com.example.pressreview.data


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    var id: Any?,
    @SerializedName("name")
    var name: String?
)