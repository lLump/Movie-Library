package com.example.mymovielibrary.presentation.ui.lists.viewModel.helper

import com.example.mymovielibrary.domain.lists.model.enums.ListType
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.repository.MediaManagerRepo

class MediaInserter(
    private val mediaManager: MediaManagerRepo,
) {

    suspend fun addItemsToCollection(checkedItems: List<MediaItem>, collectionId: Int) {
        val body = mediasToJson(checkedItems)
        mediaManager.addMediasToCollection(collectionId, body)
    }

    suspend fun putOrDeleteItemsInChosenList(
        checkedItems: List<MediaItem>,
        listType: ListType,
        isAdding: Boolean,
    ) {
        when (listType) {
            ListType.WATCHLIST -> {
                checkedItems.forEach { media ->
                    mediaManager.addOrDeleteInWatchlist(
                        mediaId = media.id,
                        isMovie = media.isMovie,
                        isAdding = isAdding
                    )
                }
            }

            ListType.RATED -> {
                checkedItems.forEach { media ->
                    mediaManager.addOrDeleteRating( //не реализовано
                        mediaId = media.id,
                        isMovie = media.isMovie,
                        isAdding = isAdding
                    )
                }
            }

            ListType.FAVORITE -> {
                checkedItems.forEach { media ->
                    mediaManager.addOrDeleteInFavorite(
                        mediaId = media.id,
                        isMovie = media.isMovie,
                        isAdding = isAdding
                    )
                }
            }

            ListType.COLLECTION -> TODO()
        }
    }

    private fun mediasToJson(items: List<MediaItem>): String {
        val stringBuilder = StringBuilder().append("{\"items\":[")
        items.forEachIndexed { index, media ->
            val mediaType = if (media.isMovie) "movie" else "tv"
            stringBuilder.append("{\"media_type\":\"$mediaType\",\"media_id\":${media.id}}")
            if (index < items.size - 1) {
                stringBuilder.append(",")
            }
        }
        stringBuilder.append("]}")
        return stringBuilder.toString()
    }
}