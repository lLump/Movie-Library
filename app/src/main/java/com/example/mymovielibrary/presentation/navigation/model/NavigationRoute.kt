package com.example.mymovielibrary.presentation.navigation.model

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    //    NavBar
    @Serializable
    data object Home: NavigationRoute
    @Serializable
    data object Profile: NavigationRoute
    @Serializable
    data object Lists: NavigationRoute
    //    NavBar

    @Serializable
    data class UniversalList(val listType: String): NavigationRoute

    @Serializable
    data class CollectionDetails(val collectionId: Int): NavigationRoute

    @Serializable
    data class MediaDetails(val mediaId: Int): NavigationRoute

    @Serializable
    data class PersonDetails(val personId: Int): NavigationRoute

    @Serializable
    data object Settings : NavigationRoute

}