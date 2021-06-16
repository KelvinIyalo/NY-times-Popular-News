package com.example.pressreview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.navArgs
import com.example.pressreview.R
import com.example.pressreview.databinding.NewsDetailsBinding
import com.example.pressreview.model.MyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetails:Fragment(R.layout.news_details) {
    lateinit var binding: NewsDetailsBinding
    private val viewModel: MyViewModel by viewModels()
   private val args:NewsDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = NewsDetailsBinding.inflate(layoutInflater)


        val article = args.article
        binding.newsWebView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }



        binding.floatingActionButton.setOnClickListener{
                viewModel.Updert(article)
            Snackbar.make(it,"Article saved Successfully", Snackbar.LENGTH_SHORT).show()
        }





       return binding.root }
}