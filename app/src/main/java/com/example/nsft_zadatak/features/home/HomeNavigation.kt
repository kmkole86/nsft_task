package com.example.nsft_zadatak.features.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.nsft_zadatak.features.home.favourites.FavouritesGraphScreen
import com.example.nsft_zadatak.features.home.repos_list.ReposListGraphScreen
import kotlinx.serialization.Serializable

@Serializable
object ReposListGraphRoute

@Serializable
object FavouritesGraphRoute

@Serializable
data class RepositoryDetailsRoute(val repoId: Int)

fun NavGraphBuilder.reposGraph() {
    composable<ReposListGraphRoute> {
        ReposListGraphScreen()
    }
}

fun NavGraphBuilder.favouritesGraph() {
    composable<FavouritesGraphRoute> {
        FavouritesGraphScreen()
    }
}

fun NavController.navigateToRepoDetails(repoId: Int) {
    navigate(route = RepositoryDetailsRoute(repoId = repoId))
}

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

val homeTopLevelRoutes = listOf(
    TopLevelRoute<ReposListGraphRoute>(
        name = "Repos",
        route = ReposListGraphRoute,
        icon = Icons.Outlined.Search
    ),
    TopLevelRoute<FavouritesGraphRoute>(
        name = "Favourites",
        route = FavouritesGraphRoute,
        icon = Icons.Outlined.FavoriteBorder
    )
)