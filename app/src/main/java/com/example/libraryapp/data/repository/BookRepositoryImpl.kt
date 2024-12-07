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



}



