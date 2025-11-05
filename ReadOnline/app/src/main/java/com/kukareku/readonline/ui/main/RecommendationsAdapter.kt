package com.kukareku.readonline.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.kukareku.readonline.Book
import com.kukareku.readonline.R

class RecommendationsAdapter(
    private val items: List<Book>,
    private val onClick: (Book) -> Unit
) : RecyclerView.Adapter<RecommendationsAdapter.BookViewHolder>() {

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: MaterialCardView = view as MaterialCardView
        val cover: ImageView = view.findViewById(R.id.book_cover)
        val title: TextView = view.findViewById(R.id.book_title)
        val author: TextView = view.findViewById(R.id.book_author)
        val price: TextView = view.findViewById(R.id.book_price)
        val badge: TextView = view.findViewById(R.id.new_badge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book_recommendation, parent, false)
        return BookViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = items[position]

        holder.title.text = book.title
        holder.author.text = book.author
        holder.price.text = book.price

        // TODO: Подключи Glide/Picasso, если нужен реальный coverUrl
        holder.cover.setImageResource(R.drawable.ic_book_24)

        holder.badge.visibility = if (book.isNew) View.VISIBLE else View.GONE

        holder.root.setOnClickListener { onClick(book) }
    }

    override fun getItemCount(): Int = items.size
}
