package cl.emilym.bookmark.ui.list

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.emilym.bookmark.LocalBackStack
import cl.emilym.bookmark.ui.entry.EntryNavigation

@Composable
fun ListScreen(viewModel: ListViewModel = viewModel()) {
    val backStack = LocalBackStack.current

    ListContent(
        viewModel.state.collectAsStateWithLifecycle().value,
        onOpenBookmark = {
            backStack.add(EntryNavigation(it))
        }
    )
}