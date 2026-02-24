package com.example.mymovielibrary.presentation.ui.lists.screen.dialogs

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.account.model.UserCollectionInfo
import com.example.mymovielibrary.domain.lists.model.enums.ListType

private data class ListTypeInfo(
    @StringRes val textId: Int,
    val icon: ImageVector,
    val listType: ListType,
)

@Composable
fun AddMediasDialog( //also from mediaDetails
    userCollections: List<UserCollectionInfo>,
    currentList: ListType? = null,
    currentCollectionId: Int? = null,
    onDismiss: () -> Unit,
    onListChosen: (ListType) -> Unit,
    onCollectionChosen: (Int) -> Unit,
) {
    var selectedOption by remember { mutableStateOf(ListType.WATCHLIST) }
    var selectedCollection by remember { mutableIntStateOf(0) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(id = R.string.add_to)) },
        text = {
            val listTypesInfo = listOf(
                ListTypeInfo(
                    textId = R.string.watchlist,
                    icon = Icons.Default.Bookmarks,
                    listType = ListType.WATCHLIST,
                ),
//                ListTypeInfo(
//                    textId = R.string.rated,
//                    icon = Icons.Default.Star,
//                    listType = ListType.RATED,
//                ),
                ListTypeInfo(
                    textId = R.string.favorite,
                    icon = Icons.Default.Favorite,
                    listType = ListType.FAVORITE,
                )
            )
            Column {
                listTypesInfo.forEach { listInfo ->
                    if (listInfo.listType != currentList) {
                        ListTypeField(
                            textId = listInfo.textId,
                            icon = listInfo.icon,
                            selectedType = selectedOption,
                            listTypeToPick = listInfo.listType
                        ) { pickedType ->
                            selectedOption = pickedType
                            selectedCollection = 0
                        }
                    }
                }
                Column {
                    Text(
                        text = stringResource(R.string.collections) + ":",
                        fontSize = 24.sp,
                    )
                    userCollections.forEach { collection ->
                        if (currentCollectionId != collection.id) { //дабы не видеть коллекцию в списке, если диалог вызван из коллекции
                            CollectionTypeField(
                                text = collection.name,
                                collectionIdToPick = collection.id,
                                selectedId = selectedCollection
                            ) { pickedCollection ->
                                selectedCollection = pickedCollection
                                selectedOption = ListType.COLLECTION
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedOption == ListType.COLLECTION) {
                        onCollectionChosen(selectedCollection)
                    } else {
                        onListChosen(selectedOption)
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
            contentDescription = null
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
private fun CollectionTypeField(
    text: String,
    collectionIdToPick: Int,
    selectedId: Int,
    onClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 16.dp)
            .clickable { onClick(collectionIdToPick) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
        )
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(
            selected = selectedId == collectionIdToPick,
            onClick = { onClick(collectionIdToPick) }
        )
    }
}