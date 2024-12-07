package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.repository.BookRepositoryProvider
import com.example.libraryapp.domain.model.Book

class AddBookUseCase {
    private val repository = BookRepositoryProvider.provideRepository()

    //Llamamos a la función invoke que recibe como parámetro el libro que va a agregar
    suspend operator fun invoke(
        title: String,
        author: String,
        year: Int,
        description: String,
        ): Book {

        require(title.isNotBlank()) { "Title cannot be blank" }
        require(author.isNotBlank()) { "Author cannot be blank" }
        require(year > 0) { "Year must be greater than 0" }
        require(description.isNotBlank()) { "Description cannot be blank" }

        val book = Book(
            id = 0,
            title = title,
            author = author,
            year = year,
            description = description,
            isAvailable = true
        )

        return repository.addBook(book)

    }
}