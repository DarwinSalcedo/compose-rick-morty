package com.paging.compose.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(val baseRoute: String, private val navArgs: List<NavArgs> = emptyList()) {

    val route = run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute).plus(argKeys).joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

    object Main : NavItem("main")
    object Detail : NavItem("detail", listOf(NavArgs.Id)) {
        fun createRoute(id: Int) = "$baseRoute/$id"
    }
}

enum class NavArgs(val key: String, val navType: NavType<*>) {
    Id("index", NavType.IntType)
}
