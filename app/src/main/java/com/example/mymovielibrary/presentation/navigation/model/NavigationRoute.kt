package com.example.mymovielibrary.presentation.navigation.model

sealed interface NavigationRoute {
    operator fun invoke(): String
}

enum class Screen(private val route: String) : NavigationRoute {
    HOME("home"),
    PROFILE("profile"),
    LISTS("favorite");

     override operator fun invoke() = route
}