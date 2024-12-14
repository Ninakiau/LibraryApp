package com.example.libraryapp.data.repository

import com.example.libraryapp.data.datasource.LocalBookDataSource
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.repository.BookRepository

class BookRepositoryImpl: BookRepository {

    private val localDataSource = LocalBookDataSource()

    override suspend fun getBooks(): List<Book> {
        return try {
            localDataSource.getBooks()
        } catch (e: Exception) {
            throw Exception("Error fetching books", e)
        }
    }

    override suspend fun addBook(book: Book): Book {
        return try {
            localDataSource.addBook(book)
        } catch (e: Exception) {
            throw Exception("Error adding book", e)
        }
    }

    override suspend fun getBookById(id: Int): Book {
        return try {
            val book = localDataSource.getBook(id)
            book ?: throw Exception("Book with ID $id not found")
        } catch (e: Exception) {
            throw Exception("Error fetching book by ID", e)
        }
    }

    override suspend fun toggleBookAvailability(id: Int): Book {
        val book = getBookById(id)
        val updatedBook = book.copy(isAvailable = !book.isAvailable)
        return updateBook(updatedBook)
    }

    override suspend fun updateBook(book: Book): Book {
        return try {
            localDataSource.updateBook(book)
        } catch (e: Exception) {
            throw Exception("Error updating book", e)
        }
    }

    override suspend fun deleteBook(id: Int) {
        return try {
            localDataSource.deleteBook(id)
        } catch (e: Exception) {
            throw Exception("Error deleting book", e)
        }
    }



}



