package com.example.pressreview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pressreview.constants.IMAGE_PLACEHOLDER
import com.example.pressreview.constants.getProgressDrawable
import com.example.pressreview.constants.loadImage
import com.example.pressreview.databinding.NewsLayoutBinding
import com.example.pressreview.module.Result
import javax.inject.Inject

class RecyclerViewAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            NewsImage.loadImage(
                article.media[0].mediaMetadata[0].url,
                getProgressDrawable(context)
            )
            tvWebHost.text = article.source
            tvDate.text = article.published_date
            Headline.text = article.title
            body.text = article.abstract
            cardBg.setOnClickListener {
                onItemCliclListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)

    private var onItemCliclListener: ((Result) -> Unit)? = null
    fun setOnItemClickListener(Listener: (Result) -> Unit) {
        onItemCliclListener = Listener
    }
}

class ViewHolder(val binding: NewsLayoutBinding) : RecyclerView.ViewHolder(binding.root)