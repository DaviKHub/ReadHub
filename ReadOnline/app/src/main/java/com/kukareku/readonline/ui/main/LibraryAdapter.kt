package com.kukareku.readonline.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.kukareku.readonline.Book
import com.kukareku.readonline.R

class LibraryAdapter(
    private val books: List<Book>,
    private var isGrid: Boolean,
    private val onAction: (Book, String) -> Unit
) : RecyclerView.Adapter<LibraryAdapter.BookViewHolder>() {

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val card: MaterialCardView = view as MaterialCardView
        private val cover: ImageView = view.findViewById(R.id.book_cover)
        private val title: TextView = view.findViewById(R.id.book_title)
        private val author: TextView = view.findViewById(R.id.book_author)
        private val genre: TextView = view.findViewById(R.id.book_genre)
        private val rating: TextView = view.findViewById(R.id.book_rating)
        private val progress: ProgressBar = view.findViewById(R.id.reading_progress)
        private val lastRead: TextView = view.findViewById(R.id.last_read)
        private val menuBtn: ImageButton = view.findViewById(R.id.book_menu)

        fun bind(book: Book) {
            cover.setImageResource(R.drawable.ic_book_24)
            title.text = book.title
            author.text = book.author
            genre.text = book.genre
            rating.text = book.rating.toString()
            progress.progress = book.progress
            lastRead.text = book.lastRead

            card.setOnClickListener { onAction(book, "open") }
            menuBtn.setOnClickListener { onAction(book, "menu") }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book_library, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size

    fun updateViewMode(isGridView: Boolean) {
        this.isGrid = isGridView
        notifyDataSetChanged()
    }
}
