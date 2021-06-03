package com.example.pressreview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.navArgs
import com.example.pressreview.R
import com.example.pressreview.databinding.NewsDetailsBinding
import com.example.pressreview.model.MyViewModel

class NewsDetails:Fragment(R.layout.news_details) {
    lateinit var binding: NewsDetailsBinding
    lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = NewsDetailsBinding.inflate(layoutInflater)










       return binding.root }
}