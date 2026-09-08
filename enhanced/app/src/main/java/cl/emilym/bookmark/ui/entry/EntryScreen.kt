package cl.emilym.bookmark.ui.entry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.emilym.bookmark.LocalBackStack

@Composable
fun EntryScreen(
    entry: EntryNavigation,
    viewModel: EntryViewModel = viewModel()
) {
    val backStack = LocalBackStack.current

    LaunchedEffect(entry.id) {
        viewModel.init(entry.id)
    }

    LaunchedEffect(viewModel) {
        viewModel.event.collect {
            when (it) {
                is EntryEvent.OnSuccess -> backStack.removeLastOrNull()
            }
        }
    }

    EntryContent(
        uiState = viewModel.state.collectAsStateWithLifecycle().value,
        controller = viewModel,
    )
}