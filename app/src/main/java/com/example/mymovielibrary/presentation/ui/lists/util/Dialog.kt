package com.example.mymovielibrary.presentation.ui.lists.util

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.lists.model.ListType
import com.example.mymovielibrary.domain.lists.model.SortType

@Composable
fun AddMediasDialog(onDismiss: () -> Unit, onSuccess: (ListType) -> Unit) {
    var selectedOption by remember { mutableStateOf(ListType.WATCHLIST) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(id = R.string.sort_by)) },
        text = {
            Column {
                ListTypeField(
                    textId = R.string.watchlist,
                    icon = Icons.Default.Bookmarks,
                    selectedType = selectedOption,
                    listTypeToPick = ListType.WATCHLIST
                ) { pickedType ->
                    selectedOption = pickedType
                }
                ListTypeField(
                    textId = R.string.rated,
                    icon = Icons.Default.Star,
                    selectedType = selectedOption,
                    listTypeToPick = ListType.RATED
                ) { pickedType ->
                    selectedOption = pickedType
                }
                ListTypeField(
                    textId = R.string.favorite,
                    icon = Icons.Default.Favorite,
                    selectedType = selectedOption,
                    listTypeToPick = ListType.FAVORITE
                ) { pickedType ->
                    selectedOption = pickedType
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSuccess(selectedOption)
                    onDismiss()
                }
            ) {
                Text(stringResource(id = R.string.apply))
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

@Composable
private fun ListTypeField(
    @StringRes textId: Int,
    icon: ImageVector,
    selectedType: ListType,
    listTypeToPick: ListType,
    onClick: (ListType) -> Unit,
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onClick(listTypeToPick) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 11.dp, bottom = 11.dp)
                .aspectRatio(1f)
//                .fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(16.dp)) //отступ между текстом и иконкой
        Text(
            text = stringResource(id = textId),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(
            selected = selectedType == listTypeToPick,
            onClick = { onClick(listTypeToPick) }
        )
    }
}

@Composable
fun SortDialog(onDismiss: () -> Unit, onSuccess: (SortType) -> Unit) {
    var selectedOption by remember { mutableStateOf(SortType.ORIGINAL_ASCENDING) }
    var isAsc by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(id = R.string.sort_by)) },
        text = {
            Column {
                SortField(
                    textId = R.string.sort_original_order,
                    selectedOption = selectedOption,
                    sortTypeToPick = SortType.ORIGINAL_ASCENDING,
                    isAsc = isAsc,
                    ascToggle = { isAsc = it },
                    onClick = { sortType ->
                        selectedOption = sortType },

                    )
                SortField(
                    textId = R.string.sort_title,
                    selectedOption = selectedOption,
                    sortTypeToPick = SortType.TITLE_ASCENDING,
                    isAsc = isAsc,
                    ascToggle = { isAsc = it },
                    onClick = { sortType ->
                        selectedOption = sortType },

                )
                SortField(
                    textId = R.string.sort_release_date,
                    selectedOption = selectedOption,
                    sortTypeToPick = SortType.RELEASE_ASCENDING,
                    isAsc = isAsc,
                    ascToggle = { isAsc = it },
                    onClick = { sortType ->
                        selectedOption = sortType },

                    )
                SortField(
                    textId = R.string.sort_primary_release_date,
                    selectedOption = selectedOption,
                    sortTypeToPick = SortType.PRIMARY_RELEASE_ASCENDING,
                    isAsc = isAsc,
                    ascToggle = { isAsc = it },
                    onClick = { sortType ->
                        selectedOption = sortType },

                    )
                SortField(
                    textId = R.string.sort_rating,
                    selectedOption = selectedOption,
                    sortTypeToPick = SortType.RATING_ASCENDING,
                    isAsc = isAsc,
                    ascToggle = { isAsc = it },
                    onClick = { sortType ->
                        selectedOption = sortType },

                    )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isAsc) {
                        onSuccess(selectedOption)
                    } else {
                        onSuccess(toggleSortType(selectedOption))
                    }
                    onDismiss()
                }
            ) {
                Text(stringResource(id = R.string.apply))
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

@Composable
private fun SortField(
    @StringRes textId: Int,
    selectedOption: SortType,
    sortTypeToPick: SortType,
    isAsc: Boolean,
    ascToggle: (Boolean) -> Unit,
    onClick: (SortType) -> Unit,
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onClick(sortTypeToPick) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedOption == sortTypeToPick,
            onClick = { onClick(sortTypeToPick) }
        )
        Text(
            text = stringResource(id = textId),
            modifier = Modifier.weight(1f)
        )
        if (selectedOption == sortTypeToPick) {
            TextButton(
                onClick = {
                    ascToggle(!isAsc)
                    onClick(sortTypeToPick)
                }
            ) {
                if (isAsc) {
                    Icon(
                        imageVector = Icons.Default.ArrowCircleUp,
                        contentDescription = "ASC icon"
                    )
                    Text(
                        text =stringResource(id = R.string.sort_asc),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowCircleDown,
                        tint = Color.Red.copy(0.4f),
                        contentDescription = "DESC icon"
                    )
                    Text(
                        text = stringResource(id = R.string.sort_desc),
                    )
                }
            }
        }
    }
}

private fun toggleSortType(sortType: SortType): SortType {
    return when (sortType) {
        SortType.ORIGINAL_ASCENDING -> SortType.ORIGINAL_DESCENDING
        SortType.ORIGINAL_DESCENDING -> SortType.ORIGINAL_ASCENDING
        SortType.TITLE_ASCENDING -> SortType.TITLE_DESCENDING
        SortType.TITLE_DESCENDING -> SortType.TITLE_ASCENDING
        SortType.RELEASE_ASCENDING -> SortType.RELEASE_DESCENDING
        SortType.RELEASE_DESCENDING -> SortType.RELEASE_ASCENDING
        SortType.PRIMARY_RELEASE_ASCENDING -> SortType.PRIMARY_RELEASE_DESCENDING
        SortType.PRIMARY_RELEASE_DESCENDING -> SortType.PRIMARY_RELEASE_ASCENDING
        SortType.RATING_ASCENDING -> SortType.RATING_DESCENDING
        SortType.RATING_DESCENDING -> SortType.RATING_ASCENDING
    }
}