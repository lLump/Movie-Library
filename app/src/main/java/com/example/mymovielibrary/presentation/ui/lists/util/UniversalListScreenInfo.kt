package com.example.mymovielibrary.presentation.ui.lists.util

import com.example.mymovielibrary.domain.lists.model.ListType
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute

data class UniversalListScreenInfo(
    val listType: ListType,
    val onBackPress: () -> Unit,
    val navigateTo: (NavigationRoute) -> Unit,
)
