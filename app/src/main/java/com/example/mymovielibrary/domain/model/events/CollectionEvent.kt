package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.domain.lists.model.SortType
import com.example.mymovielibrary.domain.lists.model.ListType

interface CollectionEvent {
    data class LoadCollection(val id: Int): CollectionEvent
    data class UpdateCollection(val name: String, val description: String, val public: Boolean): CollectionEvent
    data class UpdateCollectionSortType(val sortType: SortType): CollectionEvent
    data class UpdateCollectionBackgroundImage(val backdropPath: String): CollectionEvent
    data class PutItemsInList(val ids: List<Int>, val listType: ListType): CollectionEvent
    data class DeleteItems(val ids: List<Int>): CollectionEvent
}