package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.usecase.UseCaseProvider
import kotlinx.coroutines.launch

class BookListViewModel : ViewModel() {

    private val getBooksUseCase = UseCaseProvider.provideGetBooksUseCase()
    private val addBookUseCase = UseCaseProvider.provideAddBookUseCase()

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _books.value = getBooksUseCase()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addBook(title: String, author: String, year: Int, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                addBookUseCase(title, author, year, description)
                loadBooks() // Recargar lista después de agregar
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}