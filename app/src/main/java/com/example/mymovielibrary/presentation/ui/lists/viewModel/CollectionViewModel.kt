package com.example.mymovielibrary.presentation.ui.lists.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.enums.SortType
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.lists.repository.MediaManagerRepo
import com.example.mymovielibrary.domain.model.events.CollectionEvent
import com.example.mymovielibrary.domain.model.events.CollectionEvent.ClearCollection
import com.example.mymovielibrary.domain.model.events.CollectionEvent.DeleteCollection
import com.example.mymovielibrary.domain.model.events.CollectionEvent.DeleteItems
import com.example.mymovielibrary.domain.model.events.CollectionEvent.LoadCollection
import com.example.mymovielibrary.domain.model.events.CollectionEvent.PutItemsInCollection
import com.example.mymovielibrary.domain.model.events.CollectionEvent.PutItemsInList
import com.example.mymovielibrary.domain.model.events.CollectionEvent.UpdateCollection
import com.example.mymovielibrary.domain.model.events.CollectionEvent.UpdateCollectionBackgroundImage
import com.example.mymovielibrary.domain.model.events.CollectionEvent.UpdateCollectionSortType
import com.example.mymovielibrary.domain.model.events.CollectionEvent.ToggleMediaCheck
import com.example.mymovielibrary.domain.model.events.CollectionEvent.ClearMediaChecks
import com.example.mymovielibrary.presentation.ui.lists.state.CollectionState
import com.example.mymovielibrary.presentation.ui.lists.viewModel.helper.MediaInserter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionRepo: CollectionRepo,
    mediaManager: MediaManagerRepo,
) : BaseViewModel() {

    private val _collectionState = MutableStateFlow(CollectionState())
    val collectionState = _collectionState.asStateFlow()

    private val mediaHelper = MediaInserter(mediaManager)

    fun onEvent(event: CollectionEvent) {
        when (event) {
            is LoadCollection -> viewModelScope.launch(Dispatchers.IO) {
                loadChosenCollection(event.id)
            }
            is UpdateCollection -> updateCollection(event.name, event.description, event.public)
            is UpdateCollectionSortType -> updateCollectionSortType(event.sortType)
            is UpdateCollectionBackgroundImage -> updateCollectionBackgroundImage(event.backdropPath)
            is PutItemsInList -> viewModelScope.launch(Dispatchers.IO) {
                mediaHelper.putOrDeleteItemsInChosenList(
                    checkedItems = getCheckedItems(event.ids),
                    listType = event.listType,
                    isAdding = true
                )
            }
            is PutItemsInCollection -> viewModelScope.launch(Dispatchers.IO) {
                mediaHelper.addItemsToCollection(
                    checkedItems = getCheckedItems(event.ids),
                    collectionId = event.collectionId
                )
            }
            is DeleteItems -> deleteCheckedItems(event.ids)
            is ClearCollection -> clearCollection(event.collectionId)
            is DeleteCollection -> deleteCollection(event.collectionId)
            is ToggleMediaCheck -> toggleMediaChecked(event.id)
            ClearMediaChecks -> clearMediaChecks()
        }
    }

    private fun clearCollection(collectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            request { collectionRepo.clearCollection(collectionId) }
            loadChosenCollection(collectionId) // load list to update the view
        }
    }

    private fun deleteCollection(collectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            request { collectionRepo.deleteCollection(collectionId) }
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

    private fun toggleMediaChecked(id: Int) {
        _collectionState.update { state ->
            state.copy(
                checkedMedias =
                    if (id in state.checkedMedias)
                        state.checkedMedias - id
                    else
                        state.checkedMedias + id
            )
        }
    }

    private fun clearMediaChecks() {
        _collectionState.update { it.copy(checkedMedias = emptySet()) }
    }
    private fun deleteCheckedItems(ids: Set<Int>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteCheckedItemsInApi(ids)
            }
            deleteCheckedItemsInState(ids)
        }
    }

    private suspend fun deleteCheckedItemsInState(ids: Set<Int>) {
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
        // тупой костыль, чем больше удалено единовременно элементов, тем дольше изменяются детали
        // да так что 5+ за раз, обновляется более 10 секунд, еще и не один раз с изначально неверными данными
        delay((1500 * ids.count()).toLong())
        loadChosenCollection(_collectionState.value.collection.id) //to update banner (Collection info)
    }

    private suspend fun deleteCheckedItemsInApi(ids: Set<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            val itemsToDelete = getCheckedItems(ids)
            val body = mediasToJson(itemsToDelete)
            request { collectionRepo.deleteItemInCollection(_collectionState.value.collection.id, body) }
        }
    }

    private fun updateCollection(name: String, description: String, public: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentId = _collectionState.value.collection.id
            request { collectionRepo.updateCollectionInfo(
                name = name,
                description = description,
                public = public,
                collectionId = currentId
            ) }
            loadChosenCollection(currentId) // load list to update the view
        }
    }

    private fun updateCollectionSortType(sortType: SortType) {
        val currentId = _collectionState.value.collection.id
        viewModelScope.launch(Dispatchers.IO) {
            request { collectionRepo.updateCollectionSortType(sortType, currentId) }
            loadChosenCollection(currentId) // load list to update the view
        }
    }
    private fun updateCollectionBackgroundImage(backdropPath: String) {
        val currentId = _collectionState.value.collection.id
        viewModelScope.launch(Dispatchers.IO) {
            request { collectionRepo.updateCollectionBackgroundPhoto(backdropPath, currentId) }
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

    private fun getCheckedItems(ids: Set<Int>): List<MediaItem> {
        return _collectionState.value.collection.movies.filter { it.id in ids }
    }
}