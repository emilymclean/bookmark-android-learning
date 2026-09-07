package cl.emilym.bookmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.emilym.bookmark.ui.theme.BookmarkTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookmarkTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(stringResource(R.string.app_name))
                            }
                        )
                    }
                ) { innerPadding ->
                    val uiState by viewModel.state.collectAsStateWithLifecycle()
                    Box(Modifier.fillMaxSize().padding(innerPadding)) {
                        when (uiState.books) {
                            is MainUiStateBooks.Success -> {
                                Column(
                                    Modifier.padding(horizontal = 16.dp)
                                ) {
                                    Column(
                                        Modifier.padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        TextField(
                                            viewModel.pageCount,
                                            modifier = Modifier.fillMaxWidth(),
                                            label = {
                                                Text(stringResource(R.string.bookmark_current_page))
                                            },
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Number,
                                                imeAction = ImeAction.Done
                                            ),
                                            onKeyboardAction = { viewModel.saveBookmark() }
                                        )

                                        Spacer(Modifier.height(16.dp))

                                        Button(
                                            onClick = {
                                                viewModel.saveBookmark()
                                            }
                                        ) {
                                            Text(stringResource(R.string.bookmark_save))
                                        }
                                    }
                                }
                            }
                            is MainUiStateBooks.Loading -> CircularProgressIndicator()
                            is MainUiStateBooks.Error -> {
                                Column(
                                    Modifier.padding(horizontal = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(stringResource(R.string.error_something_went_wrong))

                                    Spacer(Modifier.height(16.dp))

                                    Button(
                                        onClick = {
                                            viewModel.saveBookmark()
                                        }
                                    ) {
                                        Text(stringResource(R.string.error_retry))
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

}