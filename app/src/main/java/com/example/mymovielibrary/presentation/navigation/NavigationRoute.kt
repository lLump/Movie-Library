package com.example.mymovielibrary.presentation.navigation

interface NavigationRoute {
    operator fun invoke(): String
}

enum class Screen(private val route: String) : NavigationRoute {
    AUTH("auth"),
    HOME("home"),
    PROFILE("profile"),
    LISTS("favorite");

     override operator fun invoke() = route
}

enum class Navigation(private val route: String) : NavigationRoute {
    MAIN("mainScreen");

    override operator fun invoke() = route
}

