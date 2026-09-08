package cl.emilym.bookmark.ui.entry

sealed interface EntryEvent {
    data object OnSuccess: EntryEvent
}