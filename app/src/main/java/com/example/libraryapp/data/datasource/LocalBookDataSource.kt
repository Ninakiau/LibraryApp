package com.example.libraryapp.data.datasource

import com.example.libraryapp.domain.model.Book
import kotlinx.coroutines.delay

class LocalBookDataSource {

    suspend fun getBooks(): List<Book> {
        delay(1000) // Simulamos delay de red/BD
        return BookDatabase.getBooks()
    }

    suspend fun getBook(id: Int): Book? {
        delay(500)
        return BookDatabase.getBook(id)
    }

    suspend fun addBook(book: Book) : Book {
        delay(500)
        BookDatabase.addBook(book)
        return book

    }

    suspend fun updateBook(book: Book): Book {
        delay(500)
        BookDatabase.updateBook(book)
        return book
    }

    suspend fun deleteBook(id: Int) {
        delay(500)
        BookDatabase.deleteBook(id)
    }

}