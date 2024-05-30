package com.example.mymovielibrary.presentation.navigation.model

sealed interface NavigationRoute {
    operator fun invoke(): String
}

enum class Screen(private val route: String) : NavigationRoute {
    HOME("home"),
    PROFILE("profile"),
    LISTS("lists"),

    COLLECTIONS("collections"),
    FAVORITES("favorites"),
    RATED("rated"),
    WATCHLIST("watchlist");

     override operator fun invoke() = route
}