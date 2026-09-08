package cl.emilym.bookmark.ui.entry

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.emilym.bookmark.data.Bookmark
import cl.emilym.bookmark.data.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
): ViewModel(), EntryController {

    private var id = 0
    override val titleField = TextFieldState()
    override val pageField = TextFieldState()

    private val _state = MutableStateFlow(EntryUiState(false))
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<EntryEvent>()
    val event = _event.asSharedFlow()

    private var saveJob: Job? = null

    override fun init(id: Int) {
        viewModelScope.launch {
            Log.d("EntryViewModel", "ID = $id")
            _state.update { it.copy(entryEnabled = false) }
            this@EntryViewModel.id = id
            val existing = if (id == 0) null else bookmarkRepository.get(id).first()

            if (existing == null) {
                titleField.setTextAndPlaceCursorAtEnd("")
                pageField.setTextAndPlaceCursorAtEnd("")
            } else {
                titleField.setTextAndPlaceCursorAtEnd(existing.title)
                pageField.setTextAndPlaceCursorAtEnd("${existing.page}")
            }
            _state.update { it.copy(entryEnabled = true) }
        }
    }

    override fun save() {
        val title = titleField.text.toString()
        val pageText = pageField.text.toString()
        val page = pageText.toIntOrNull() ?: 0
        if (title.isBlank()) return

        if (saveJob?.isActive == true) return
        saveJob = viewModelScope.launch {
            _state.update { it.copy(entryEnabled = false) }
            id = bookmarkRepository.save(Bookmark(id, title, page))

            _event.emit(EntryEvent.OnSuccess)
            _state.update { it.copy(entryEnabled = true) }
        }
    }
}