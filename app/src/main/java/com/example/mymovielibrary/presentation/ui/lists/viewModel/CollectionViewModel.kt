package com.example.mymovielibrary.presentation.ui.lists.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.model.ListType
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.SortType
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.lists.repository.MediaManager
import com.example.mymovielibrary.domain.model.events.CollectionEvent
import com.example.mymovielibrary.domain.model.events.CollectionEvent.DeleteItems
import com.example.mymovielibrary.domain.model.events.CollectionEvent.LoadCollection
import com.example.mymovielibrary.domain.model.events.CollectionEvent.PutItemsInList
import com.example.mymovielibrary.domain.model.events.CollectionEvent.UpdateCollection
import com.example.mymovielibrary.domain.model.events.CollectionEvent.UpdateCollectionBackgroundImage
import com.example.mymovielibrary.domain.model.events.CollectionEvent.UpdateCollectionSortType
import com.example.mymovielibrary.presentation.ui.lists.state.CollectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionRepo: CollectionRepo,
    private val mediaManager: MediaManager,
) : BaseViewModel() {

    private val _collectionState = MutableStateFlow(CollectionState())
    val collectionState = _collectionState.asStateFlow()

    fun onEvent(event: CollectionEvent) {
        when (event) {
            is LoadCollection -> viewModelScope.launch(Dispatchers.IO) {
                loadChosenCollection(event.id)
            }
            is UpdateCollection -> updateCollection(event.name, event.description, event.public)
            is UpdateCollectionSortType -> updateCollectionSortType(event.sortType)
            is UpdateCollectionBackgroundImage -> updateCollectionBackgroundImage(event.backdropPath)
            is PutItemsInList -> putItemsInSomeList(event.ids, event.listType)
            is DeleteItems -> deleteCheckedItems(event.ids)
        }
    }

    private suspend fun loadChosenCollection(collectionId: Int) {
        val collectionDetails = request { collectionRepo.getCollectionDetails(collectionId) }
        if (collectionDetails != null) {
            _collectionState.emit(
                CollectionState(
                    isLoading = false,
                    collection = collectionDetails
                )
            )
        }
    }

    private fun isItemInCollection(collectionId: Int, mediaId: Int, mediaType: String) {
        viewModelScope.launch {
            mediaManager.checkIfMediaInCollection(collectionId, mediaId, mediaType)
        }
    }

    private fun addItemsToCollection(ids: List<Int>) {
        viewModelScope.launch {
            val body = mediasToJson(getCheckedItems(ids))
            mediaManager.addMediasToCollection(_collectionState.value.collection.id, body)
        }
    }

    private fun putItemsInSomeList(ids: List<Int>, listType: ListType) {
        viewModelScope.launch(Dispatchers.IO) {
            val itemsToPut = getCheckedItems(ids)
            when (listType) {
                ListType.WATCHLIST -> {
                    itemsToPut.forEach { media ->
                        mediaManager.addOrDeleteInWatchlist(
                            mediaId = media.id,
                            isMovie = media.isMovie,
                            isAdding = true
                        )
                    }
                }
                ListType.RATED -> {
                    itemsToPut.forEach { media ->
                        mediaManager.addOrDeleteRating( //не реализовано
                            mediaId = media.id,
                            isMovie = media.isMovie,
                            isAdding = true
                        )
                    }
                }
                ListType.FAVORITE -> {
                    itemsToPut.forEach { media ->
                        mediaManager.addOrDeleteInFavorite(
                            mediaId = media.id,
                            isMovie = media.isMovie,
                            isAdding = true
                        )
                    }
                }
            }
        }
    }

    private fun deleteCheckedItems(ids: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCheckedItemsInApi(ids)
            deleteCheckedItemsInState(ids)
        }
    }

    private suspend fun deleteCheckedItemsInState(ids: List<Int>) {
        val newMovies = _collectionState.value.collection.movies.filter { it.id !in ids }
        _collectionState.emit(
            _collectionState.value.copy(
                isLoading = false,
                collection = _collectionState.value.collection.copy(
                    movies = newMovies,
                    itemsCount = newMovies.count().toString(),
                )
            )
        )
        // SERVER BUG FIXME
        // вся инфа на сервере не успевает обновиться
        // тупой костыль, чем больше удалено единовременно элементов, тем дольше изменяються детали
        // да так что 5+ за раз, обновляется более 10 секунд, еще и не один раз с изначально неверными данными
        delay((1500 * ids.count()).toLong())
        loadChosenCollection(_collectionState.value.collection.id) //to update banner (Collection info)
    }

    private suspend fun deleteCheckedItemsInApi(ids: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            val itemsToDelete = getCheckedItems(ids)
            val body = mediasToJson(itemsToDelete)
            collectionRepo.deleteItemInCollection(_collectionState.value.collection.id, body)
        }
    }

    private fun updateCollection(name: String, description: String, public: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionRepo.updateCollectionInfo(
                name = name,
                description = description,
                public = public,
                collectionId = _collectionState.value.collection.id
            )
        }
    }

    private fun updateCollectionSortType(sortType: SortType) {
        val currentId = _collectionState.value.collection.id
        viewModelScope.launch(Dispatchers.IO) {
            collectionRepo.updateCollectionSortType(sortType, currentId)
            loadChosenCollection(currentId) // load list to update the view
        }
    }
    private fun updateCollectionBackgroundImage(backdropPath: String) {
        val currentId = _collectionState.value.collection.id
        viewModelScope.launch(Dispatchers.IO) {
            collectionRepo.updateCollectionBackgroundPhoto(backdropPath, currentId)
            loadChosenCollection(currentId) // load list to update the view
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

    private fun getCheckedItems(ids: List<Int>): List<MediaItem> {
        return _collectionState.value.collection.movies.filter { it.id in ids }
    }
}