package android.example.newsapp.adapters

import android.example.newsapp.database.NewsProperty
import android.example.newsapp.databinding.ListNewsItemBinding
import android.example.newsapp.screens.newslist.NewsListViewModel
import android.example.newsapp.utils.ImageClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class NewsListDiffCallback : DiffUtil.ItemCallback<NewsProperty>() {
    override fun areItemsTheSame(oldItem: NewsProperty, newItem: NewsProperty): Boolean {
        return oldItem.readMoreUrl == newItem.readMoreUrl
    }

    override fun areContentsTheSame(oldItem: NewsProperty, newItem: NewsProperty): Boolean {
        return oldItem == newItem
    }
}

class NewsListAdapter(private val newsListViewModel: NewsListViewModel, private val clickListener: ImageClickListener) : ListAdapter<NewsProperty, NewsListAdapter.NewsListViewHolder>(NewsListDiffCallback()) {
    class NewsListViewHolder(binding: ListNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // views in the list item
        val newsImage = binding.image
        val authorText = binding.author
        val titleText = binding.title
        val dateText = binding.dateText

        val shareBtn = binding.shareBtn
        val readMoreBtn = binding.readMoreBtn

        companion object {
            // inflate the list item layout
            fun from(parent: ViewGroup): NewsListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListNewsItemBinding.inflate(layoutInflater, parent, false)
                return NewsListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val newsProperty = getItem(position)

        Picasso.get().load(newsProperty.imageUrl).into(holder.newsImage)
        holder.authorText.text = newsProperty.author
        holder.titleText.text = newsProperty.title
        // title text should be underlined
        holder.titleText.paint.isUnderlineText = true
        holder.dateText.text = "${newsProperty.date}, ${newsProperty.time}"

        // open dialog
        holder.newsImage.setOnClickListener {
            clickListener.onImageClicked(newsProperty.imageUrl)
        }

        // share news
        holder.shareBtn.setOnClickListener {
            newsListViewModel.shareNews(newsProperty.title, newsProperty.readMoreUrl)
        }

        holder.titleText.setOnClickListener {
            newsListViewModel.onClickNewsItem(newsProperty)
        }

        // open news details
        holder.readMoreBtn.setOnClickListener {
            newsListViewModel.onClickNewsItem(newsProperty)
        }
    }
}