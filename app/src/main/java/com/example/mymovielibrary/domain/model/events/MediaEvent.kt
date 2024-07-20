package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.domain.lists.model.enums.ListType

sealed interface MediaEvent {
    data class LoadChosenList(val listType: ListType): MediaEvent
    data class PutItemsInList(val ids: List<Int>, val listType: ListType): MediaEvent
    data class PutItemsInCollection(val ids: List<Int>, val collectionId: Int): MediaEvent
    data class DeleteItems(val ids: List<Int>, val listType: ListType): MediaEvent
}