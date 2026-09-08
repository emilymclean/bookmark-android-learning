package cl.emilym.bookmark.ui.entry

import androidx.compose.foundation.text.input.TextFieldState

interface EntryController {

    val titleField: TextFieldState
    val pageField: TextFieldState

    fun init(id: Int)

    fun save()

}