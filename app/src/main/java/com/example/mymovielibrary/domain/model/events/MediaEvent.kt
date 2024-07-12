package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.domain.lists.model.ListType

sealed interface MediaEvent {
    data class LoadChosenList(val listType: ListType): MediaEvent
    data class DeleteItems(val ids: List<Int>, val type: ListType): MediaEvent
    data class EditItems(val ids: List<Int>, val type: ListType): MediaEvent
}