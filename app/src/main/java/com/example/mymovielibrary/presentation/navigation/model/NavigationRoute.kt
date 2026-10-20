package com.example.mymovielibrary.presentation.navigation.model

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    //    NavBar
    @Serializable
    data object HomeRoute: NavigationRoute
    @Serializable
    data object ProfileRoute: NavigationRoute
    @Serializable
    data object ListsRoute: NavigationRoute
    //    NavBar

    @Serializable
    data class UniversalListRoute(val listType: String): NavigationRoute

    @Serializable
    data class CollectionDetailsRoute(val collectionId: Int): NavigationRoute

    @Serializable
    data class MediaDetailsRoute(val mediaId: Int): NavigationRoute

    @Serializable
    data class PersonDetailsRoute(val personId: Int): NavigationRoute

    @Serializable
    data object SettingsRoute : NavigationRoute

}