package com.example.pressreview.view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.pressreview.R
import com.example.pressreview.databinding.NewsDetailsBinding
import com.example.pressreview.model.MyViewModel
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
            article.url.let {
                loadUrl(it)
            }
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            webViewClient = object :WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                  binding.progressHorizontal.visibility = View.INVISIBLE
                    super.onPageFinished(view, url)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressHorizontal.visibility = View.VISIBLE

                    super.onPageStarted(view, url, favicon)
                }
            }
        }








       return binding.root }


}