package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.domain.lists.model.enums.ListType
import com.example.mymovielibrary.domain.lists.model.enums.SortType

interface CollectionEvent {
    data class LoadCollection(val id: Int): CollectionEvent
    data class UpdateCollection(val name: String, val description: String, val public: Boolean): CollectionEvent
    data class UpdateCollectionSortType(val sortType: SortType): CollectionEvent
    data class UpdateCollectionBackgroundImage(val backdropPath: String): CollectionEvent
    data class PutItemsInList(val ids: Set<Int>, val listType: ListType): CollectionEvent
    data class PutItemsInCollection(val ids: Set<Int>, val collectionId: Int): CollectionEvent
    data class DeleteItems(val ids: Set<Int>): CollectionEvent
    data class ClearCollection(val collectionId: Int): CollectionEvent
    data class DeleteCollection(val collectionId: Int): CollectionEvent
    data class ToggleMediaCheck(val id: Int): CollectionEvent
    data object ClearMediaChecks: CollectionEvent
}