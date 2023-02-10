package com.paging.compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paging.compose.domain.model.Character
import com.paging.compose.presentation.detail.DetailScreen
import com.paging.compose.presentation.home.HomeScreen
import com.paging.compose.presentation.home.HomeViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = NavItem.Main.route) {

        composable(NavItem.Main) {
            HomeScreen(
                onItemClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("_data", it)
                    navController.navigate(NavItem.Detail.createRoute(it.id.toInt()))
                }, hiltViewModel<HomeViewModel>()
            )
        }
        composable(NavItem.Detail) {
            val dataCharacter =
                navController.previousBackStackEntry?.savedStateHandle?.get<Character>("_data")
            dataCharacter?.let {
                DetailScreen(data = it,
                    onUpClick = { navController.popBackStack() })
            }
        }

    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = navItem.route,
        arguments = navItem.args
    ) {
        content(it)
    }
}
