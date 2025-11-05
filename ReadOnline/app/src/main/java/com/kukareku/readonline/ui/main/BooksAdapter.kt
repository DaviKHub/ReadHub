package com.kukareku.readonline.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kukareku.readonline.data.db.Book
import com.kukareku.readonline.databinding.ItemBookBinding
import android.widget.Filter
import android.widget.Filterable

class BooksAdapter(private val onClick: (Book) -> Unit)
    : ListAdapter<Book, BooksAdapter.VH>(DIFF), Filterable {

    private var fullList: List<Book> = emptyList()

    override fun submitList(list: List<Book>?) {
        fullList = list ?: emptyList()
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }

    inner class VH(private val b: ItemBookBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(book: Book) {
            b.tvTitle.text = book.title
            b.tvAuthor.text = book.author ?: ""
            b.root.setOnClickListener { onClick(book) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Book, newItem: Book) = oldItem == newItem
        }
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val q = constraint?.toString()?.lowercase()?.trim() ?: ""
                val filtered = if (q.isEmpty()) fullList else fullList.filter {
                    it.title.lowercase().contains(q) || (it.author?.lowercase()?.contains(q) ?: false)
                }
                val res = FilterResults()
                res.values = filtered
                return res
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                val list = results?.values as? List<Book> ?: emptyList()
                submitList(list)
            }
        }
    }
}
