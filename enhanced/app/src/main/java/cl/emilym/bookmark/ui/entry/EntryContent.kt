package cl.emilym.bookmark.ui.entry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cl.emilym.bookmark.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryContent(
    uiState: EntryUiState,
    controller: EntryController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.bookmark_entry_title)) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (!uiState.entryEnabled) return@FloatingActionButton
                    controller.save()
                },
            ) {
                Icon(painterResource(R.drawable.save), contentDescription = stringResource(R.string.bookmark_save))
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                state = controller.titleField,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.bookmark_title)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                enabled = uiState.entryEnabled
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                state = controller.pageField,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.bookmark_current_page)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                onKeyboardAction = {
                    controller.save()
                },
                enabled = uiState.entryEnabled
            )
        }
    }
}