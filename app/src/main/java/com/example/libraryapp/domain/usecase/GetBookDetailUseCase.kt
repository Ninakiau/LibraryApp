package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.repository.BookRepositoryProvider
import com.example.libraryapp.domain.model.Book

class GetBookDetailUseCase {
    private val repository = BookRepositoryProvider.provideRepository()

    suspend operator fun invoke(id: Int): Book {
        return repository.getBookById(id)

    }
}