package cl.emilym.bookmark

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndSelectAll
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.emilym.bookmark.data.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class MainUiState(
    val books: MainUiStateBooks
)

sealed interface MainUiStateBooks {
    data object Loading: MainUiStateBooks
    data class Success(
        val page: Int
    ): MainUiStateBooks
    data object Error: MainUiStateBooks
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {

    private val _state = MutableStateFlow(MainUiState(MainUiStateBooks.Loading))
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    val pageCount = TextFieldState(initialText = "0")

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.update { it.copy(books = MainUiStateBooks.Loading) }
            try {
                val currentPage = bookmarkRepository.get()
                _state.update { it.copy(books = MainUiStateBooks.Success(currentPage ?: 0)) }
                pageCount.setTextAndSelectAll("${currentPage ?: 0}")
            } catch (e: IOException) {
                Log.e(TAG, "Failed to load bookmark", e)
                _state.update { it.copy(books = MainUiStateBooks.Error) }
            }
        }
    }

    fun saveBookmark() {
        val page = pageCount.text.toString().trim().toIntOrNull() ?: return
        viewModelScope.launch {
            try {
                bookmarkRepository.save(page)
                _state.update { it.copy(books = MainUiStateBooks.Success(page)) }
                Log.d(TAG, "Saved bookmark page $page")
            } catch (e: IOException) {
                Log.e(TAG, "Failed to save bookmark", e)
                // In a real app we may present a toast here or something
            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }

}