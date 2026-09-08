package cl.emilym.bookmark.ui.entry

import androidx.navigation3.runtime.EntryProviderScope
import cl.emilym.bookmark.Navigation
import kotlinx.serialization.Serializable

@Serializable
data class EntryNavigation(
    val id: Int
): Navigation {
    companion object {
        val New = EntryNavigation(0)
    }
}

fun EntryProviderScope<Navigation>.provideEntry() {
    entry<EntryNavigation> {
        EntryScreen(it)
    }
}