package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.usecase.UseCaseProvider
import kotlinx.coroutines.launch

class BookDetailViewModel : ViewModel(){
    private val getBookByIdUseCase = UseCaseProvider.provideGetBookDetailUseCase()
    private val toggleBookAvailabilityUseCase = UseCaseProvider.provideToggleBookAvailabilityUseCase()

    private val _book = MutableLiveData<Book?>()
    val book: MutableLiveData<Book?> = _book

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadBook(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val bookDetail = getBookByIdUseCase(id)
                _book.value = bookDetail
                _error.value = null
            } catch (e: Exception){
                _book.value = null
                _error.value = e.localizedMessage ?: "An unknown error occurred"
            } finally {
                _loading.value = false
            }
        }
    }
    fun toggleBookAvailability(book: Book) {
        viewModelScope.launch {
            try {
                val updatedBook = toggleBookAvailabilityUseCase(book.id)
                _book.value = updatedBook

            } catch (e: Exception) {
                // Manejar errores
                _error.value = "Error toggling book availability"
            }
        }
    }
}

