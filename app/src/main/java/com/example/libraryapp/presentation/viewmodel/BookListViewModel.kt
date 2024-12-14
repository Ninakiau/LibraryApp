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
    private val toggleBookAvailabilityUseCase = UseCaseProvider.provideToggleBookAvailabilityUseCase()

    private val _books = MutableLiveData<List<Book>?>()
    val books: MutableLiveData<List<Book>?> = _books

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

    fun addBook(title: String, author: String, year: Int, description: String, isAvailable: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                addBookUseCase(title, author, year, description, isAvailable )
                loadBooks() // Recargar lista después de agregar
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun toggleBookAvailability(book: Book) {
        viewModelScope.launch {
            try {
                val updatedBook = toggleBookAvailabilityUseCase(book.id)
                // Actualizar la lista de libros
                val updatedList = _books.value?.map {
                    if (it.id == updatedBook?.id) updatedBook else it
                }
                _books.value = updatedList
            } catch (e: Exception) {
                // Manejar errores
                _error.value = "Error toggling book availability"
            }
        }
    }
}