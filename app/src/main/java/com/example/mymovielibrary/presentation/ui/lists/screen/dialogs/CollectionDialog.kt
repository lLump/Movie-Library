package com.example.mymovielibrary.presentation.ui.lists.screen.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymovielibrary.R

@Composable
fun CollectionDialog(
    isCreating: Boolean = false, // true == create / false == edit
    collectionName: String = "",
    collectionDescription: String = "",
    isPublic: Boolean = true,
    onDismiss: () -> Unit,
    onSuccess: (String, String, Boolean) -> Unit
) {

    var name by remember { mutableStateOf(collectionName) }
    var description by remember { mutableStateOf(collectionDescription) }
    var public by remember { mutableStateOf(isPublic) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(id = if (isCreating) R.string.collection_create else R.string.edit_collection)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(id = R.string.name)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(id = R.string.description)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.is_public),
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = public,
                        onCheckedChange = { public = it }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSuccess(name, description, public)
                    onDismiss()
                }
            ) {
                Text(stringResource(id = if (isCreating) R.string.create else R.string.apply))
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}