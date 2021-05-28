package com.example.pressreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.example.pressreview.data.Article
import com.example.pressreview.databinding.NewsLayoutBinding
import javax.inject.Inject

class RecyclerViewAdapter @Inject constructor(val context: GlideContext):RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NewsLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            Glide.with(context).load(article.urlToImage).into(NewsImage)
           tvWebHost.text = article.source?.name
            tvDate.text = article.publishedAt
            Headline.text = article.title
            body.text = article.description
            setOnItemClickListener{
                onItemCliclListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallback = object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffCallback)

    private var onItemCliclListener:((Article)-> Unit)? = null
    fun setOnItemClickListener(Listener:(Article) -> Unit){
        onItemCliclListener = Listener
    }
}
class ViewHolder(val binding: NewsLayoutBinding):RecyclerView.ViewHolder(binding.root)