package com.example.libraryapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.R
import com.example.libraryapp.databinding.DialogAddBookBinding
import com.example.libraryapp.databinding.FragmentBookingListBinding
import com.example.libraryapp.presentation.adapter.BookAdapter
import com.example.libraryapp.presentation.viewmodel.BookListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class BookingListFragment : Fragment(R.layout.fragment_booking_list) {

    private var _binding: FragmentBookingListBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookAdapter: BookAdapter

    private val viewModel: BookListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<FloatingActionButton>(R.id.addButton)

        setupRecyclerView()

        button.setOnClickListener {
            showAddBookDialog()
        }

        viewModel.books.observe(viewLifecycleOwner) { books ->
            bookAdapter.submitList(books)
            if (books != null) {
                binding.emptyView.isVisible = books.isEmpty()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter(
            onItemClick = { book ->
                // Navegar al detalle usando Navigation Component pasar el id
                val bundle = Bundle().apply {
                    putInt("book_id", book.id)
                }
                findNavController().navigate(
                    R.id.action_bookingListFragment_to_bookingDetailFragment,
                    bundle
                )
            },
            onAvailabilityToggle = { book ->
                viewModel.toggleBookAvailability(book)
            }
        )

        binding.recyclerView.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }
    private fun showAddBookDialog() {
        val dialogBinding = DialogAddBookBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add New Book")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                with(dialogBinding) {
                    val title = dialogBinding.titleInput.text.toString().trim()
                    val author = dialogBinding.authorInput.text.toString().trim()
                    val yearText = dialogBinding.yearInput.text.toString().trim()
                    val description = dialogBinding.descriptionInput.text.toString().trim()
                    val isAvailable = switch1.isChecked

                    if (title.isEmpty() || author.isEmpty() || yearText.isEmpty() || description.isEmpty()) {
                        Snackbar.make(binding.root, "Please fill out all fields", Snackbar.LENGTH_SHORT).show()
                    } else {
                        val year = yearText.toIntOrNull()
                        if (year == null || year <= 0) {
                            Snackbar.make(binding.root, "Invalid year", Snackbar.LENGTH_SHORT).show()
                        } else {
                            viewModel.addBook(title, author, year, description, isAvailable )
                            Snackbar.make(binding.root, "Book added successfully", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}