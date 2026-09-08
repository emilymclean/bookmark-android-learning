package cl.emilym.bookmark.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.emilym.bookmark.R
import cl.emilym.bookmark.data.Bookmark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
    uiState: ListUiState,
    onOpenBookmark: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onOpenBookmark(0) }) {
                Icon(
                    painterResource(R.drawable.add),
                    contentDescription = stringResource(R.string.bookmark_entry_add)
                )
            }
        }
    ) { innerPadding ->
        when (val bookmark = uiState.bookmarks) {
            is ListBookmarksState.Success -> LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bookmark.bookmarks) { bookmark ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        onClick = { onOpenBookmark(bookmark.id) }
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                bookmark.title,
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                "${bookmark.page}",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                }
            }

            is ListBookmarksState.Loading -> Box(modifier = Modifier.padding(innerPadding)) {
                CircularProgressIndicator()
            }

            is ListBookmarksState.Error -> Box(modifier = Modifier.padding(innerPadding)) {
                Text(stringResource(R.string.error_something_went_wrong))
            }
        }
    }
}

@Preview
@Composable
fun ListContentPreview() {
    ListContent(
        uiState = ListUiState(
            bookmarks = ListBookmarksState.Success(
                listOf(
                    Bookmark(
                        id = 1,
                        title = "Crime and Punishment",
                        page = 100
                    ),
                    Bookmark(
                        id = 2,
                        title = "Pale Fire",
                        page = 150
                    )
                )
            )
        ),
        onOpenBookmark = {}
    )
}