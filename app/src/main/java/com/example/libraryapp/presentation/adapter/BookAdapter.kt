package com.example.libraryapp.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.databinding.ItemBookBinding
import com.example.libraryapp.domain.model.Book

class BookAdapter(
    private val onItemClick: (Book) -> Unit,
    private val onAvailabilityToggle: (Book) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding, onItemClick, onAvailabilityToggle)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book,
            onItemClick = { onItemClick(book) },
            onAvailabilityToggle = { onAvailabilityToggle(book) }
        )
    }

    inner class BookViewHolder(
        private val binding: ItemBookBinding,
        private val onItemClick: (Book) -> Unit,
        private val onAvailabilityToggle: (Book) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            book: Book,
            onItemClick: () -> Unit,
            onAvailabilityToggle: () -> Unit
        ) {
            binding.apply {
                titleText.text = book.title
                authorText.text = book.author
                yearText.text = book.year.toString()

                root.setOnClickListener { onItemClick() }
                availabilityChip.setOnClickListener { onAvailabilityToggle() }

                availabilityChip.apply {
                    text = if (book.isAvailable) "Available" else "Checked Out"
                    chipBackgroundColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            if (book.isAvailable) R.color.available
                            else R.color.checked_out
                        )
                    )
                }
            }
        }
    }



    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}