package cl.emilym.bookmark.ui.list

import androidx.navigation3.runtime.EntryProviderScope
import cl.emilym.bookmark.Navigation
import kotlinx.serialization.Serializable

@Serializable
data object ListNavigation: Navigation

fun EntryProviderScope<Navigation>.provideList() {
    entry<ListNavigation> { ListScreen() }
}