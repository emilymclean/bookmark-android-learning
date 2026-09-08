package cl.emilym.bookmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import cl.emilym.bookmark.ui.entry.provideEntry
import cl.emilym.bookmark.ui.list.ListNavigation
import cl.emilym.bookmark.ui.list.provideList
import cl.emilym.bookmark.ui.theme.BookmarkTheme
import dagger.hilt.android.AndroidEntryPoint

val LocalBackStack = staticCompositionLocalOf<MutableList<Navigation>> { error("No backstack provided") }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = remember { mutableStateListOf<Navigation>(ListNavigation) }

            BookmarkTheme {
                CompositionLocalProvider(LocalBackStack provides backStack) {
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryProvider = entryProvider {
                            provideList()
                            provideEntry()
                        }
                    )
                }
            }
        }
    }

}