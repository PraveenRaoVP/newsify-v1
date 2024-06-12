package android.example.newsapp.adapters

import android.annotation.SuppressLint
import android.example.newsapp.databinding.ListCategoriesItemBinding
import android.example.newsapp.screens.newslist.NewsListViewModel
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ListItemDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class ListItemAdapter(private val newsListViewModel: NewsListViewModel) : ListAdapter<String, ListItemAdapter.ListItemViewHolder>(ListItemDiffUtil()) {

    private var lastSelectedPosition: Int? = newsListViewModel.selectedCategoryPosition.value

    class ListItemViewHolder(private val binding: ListCategoriesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val categoryItem = binding.categoryItem
        val category: String? = binding.category

        fun bind(category: String, isSelected: Boolean) {
            binding.category = category
            binding.executePendingBindings()
            categoryItem.setCardBackgroundColor(if (isSelected) Color.parseColor("#E4DDDD") else Color.parseColor("#FFFFFF"))
        }

        companion object {
            fun from(parent: ViewGroup): ListItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListCategoriesItemBinding.inflate(layoutInflater, parent, false)
                return ListItemViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val category = getItem(position)
        val isSelected = newsListViewModel.selectedCategoryPosition.value == position
        holder.bind(category, isSelected)

        holder.categoryItem.setOnClickListener {
            // if the same category is clicked, do nothing
            if(lastSelectedPosition == position) {
                return@setOnClickListener
            }
            val previousPosition = lastSelectedPosition
            lastSelectedPosition = position



            // Update ViewModel's selected position
            newsListViewModel.onClickCategory(category, position)

            // Notify adapter about item change for selected and previously selected item
            previousPosition?.let { notifyItemChanged(it) }
            notifyItemChanged(position)
        }
    }

    //        val category = getItem(position)
//        holder.bind(category)
//
//        // Check if the current category position matches the last selected position
//        if (position == lastSelectedPosition) {
//            // Change the color of the selected category to #E4DDDD
//            holder.categoryItem.setCardBackgroundColor(Color.parseColor("#E4DDDD"))
//        } else {
//            // Otherwise, set the color to white
//            holder.categoryItem.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
//        }
//
//        holder.categoryItem.setOnClickListener {
//
//            if(lastSelectedPosition == holder.absoluteAdapterPosition) {
//                return@setOnClickListener
//            }
//            // change the category in the view model
//            newsListViewModel.onClickCategory(category!!, position)
////            notifyDataSetChanged()
////             change the color of the selected category to #E4DDDD and the rest to white
//            holder.categoryItem.setCardBackgroundColor(Color.parseColor("#E4DDDD"))
//
//            // change the rest of the categories to white
//            val recyclerView = holder.itemView.parent as? RecyclerView
//            recyclerView?.let { rv ->
//                for (i in 0 until rv.childCount) {
//                    val child = rv.getChildAt(i)
//                    val viewHolder = rv.getChildViewHolder(child) as? ListItemViewHolder
//                    if (viewHolder != null && viewHolder.absoluteAdapterPosition != position) {
//                        viewHolder.categoryItem.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
//                    }
//                }
//            }
//
//            lastSelectedPosition = holder.adapterPosition
//
//        }
//    }
}