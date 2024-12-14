package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.model.Book

interface BookRepository {
    //TODO implement the other functions
    suspend fun getBooks(): List<Book>
    suspend fun addBook(book: Book): Book
    suspend fun getBookById(id: Int): Book
    suspend fun toggleBookAvailability(id: Int): Book?
    suspend fun deleteBook(id: Int)
    suspend fun updateBook(book: Book): Book
}