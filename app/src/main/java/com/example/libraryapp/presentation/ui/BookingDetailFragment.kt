package com.example.libraryapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentBookingDetailBinding
import com.example.libraryapp.presentation.viewmodel.BookDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BookingDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class BookingDetailFragment : Fragment(R.layout.fragment_booking_detail) {
    private var _binding: FragmentBookingDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookDetailViewModel by viewModels()

     private var bookId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookId = arguments?.getInt("book_id")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookId = arguments?.getInt("book_id")
        bookId?.let {
            viewModel.loadBook(it)
        }

        viewModel.book.observe(viewLifecycleOwner) { book ->
            binding.titleText.text = book?.title
            binding.authorText.text = book?.author
            binding.yearText.text = book?.year.toString()
            binding.descriptionText.text = book?.description

            // Optional: Set availability chip
            if (book != null) {
                binding.availabilityChip.text = if (book.isAvailable) "Available" else "Not Available"
            }

        }

        // Also handle loading and error states
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private const val ARG_PARAM1 = "book_id"

        fun newInstance(bookId: Int) : BookingDetailFragment {
            val fragment = BookingDetailFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, bookId)
            fragment.arguments = args
            return fragment
        }
    }
}