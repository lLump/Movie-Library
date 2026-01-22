package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.domain.lists.model.enums.ListType

sealed interface MediaEvent {
    data class LoadChosenList(val listType: ListType): MediaEvent
    data class PutItemsInList(val ids: Set<Int>, val listType: ListType): MediaEvent
    data class PutItemsInCollection(val ids: Set<Int>, val collectionId: Int): MediaEvent
    data class DeleteItems(val ids: Set<Int>, val listType: ListType): MediaEvent
    data class ToggleMediaCheck(val id: Int): MediaEvent
    data object ClearMediaChecks: MediaEvent
}