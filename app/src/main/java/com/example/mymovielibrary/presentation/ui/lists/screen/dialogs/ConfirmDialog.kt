package com.example.mymovielibrary.presentation.ui.lists.screen.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.mymovielibrary.R

enum class ConfirmDialogOptions{
    DELETE_MEDIAS, DELETE_COLLECTION, CLEAR_COLLECTION, DEFAULT
}

@Composable
fun ConfirmDialog(
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    dialogOptions: ConfirmDialogOptions,
    itemCount: Int = 0,
) {

    val text = stringResource(id = R.string.are_you_sure) + " " + when (dialogOptions) {
        ConfirmDialogOptions.DELETE_MEDIAS -> " " + stringResource(id = R.string.delete) + " $itemCount " + stringResource(id = R.string.media_items)
        ConfirmDialogOptions.DELETE_COLLECTION -> stringResource(id = R.string.delete_collection).lowercase()
        ConfirmDialogOptions.CLEAR_COLLECTION -> stringResource(id = R.string.clear_collection).lowercase()
        ConfirmDialogOptions.DEFAULT -> TODO()
    } + "?"

    AlertDialog(
        onDismissRequest = { onDismiss() },
//        title = { Text(stringResource(id = R.string.sort_by)) },
        text = {
            Column {
                Text(
                    text = text,
                    fontSize = 24.sp,
                    lineHeight = 30.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSuccess()
                    onDismiss()
                }
            ) {
                Text(stringResource(id = R.string.yes))
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text(stringResource(id = R.string.no))
            }
        }
    )
}