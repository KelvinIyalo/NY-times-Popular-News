package com.example.pressreview.module

data class NYTimesData(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)