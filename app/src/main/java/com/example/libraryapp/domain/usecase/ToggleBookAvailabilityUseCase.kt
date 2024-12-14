package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.repository.BookRepositoryProvider
import com.example.libraryapp.domain.model.Book


class ToggleBookAvailabilityUseCase {
    private val repository = BookRepositoryProvider.provideRepository()
    suspend operator fun invoke(bookId: Int): Book? {
        return repository.toggleBookAvailability(bookId)
    }
}