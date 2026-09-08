package cl.emilym.bookmark.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.emilym.bookmark.data.Bookmark
import cl.emilym.bookmark.data.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListUiState(
    val bookmarks: ListBookmarksState
)

sealed interface ListBookmarksState {
    data object Loading: ListBookmarksState
    data class Success(
        val bookmarks: List<Bookmark>
    ): ListBookmarksState
    data object Error: ListBookmarksState
}

@HiltViewModel
class ListViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {

    private val _state = MutableStateFlow(ListUiState(ListBookmarksState.Loading))
    val state: StateFlow<ListUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            bookmarkRepository.get().catch {
                _state.update { it.copy(bookmarks = ListBookmarksState.Error) }
            }.collect { bookmarks ->
                _state.update { it.copy(bookmarks = ListBookmarksState.Success(bookmarks)) }
            }
        }
    }

}