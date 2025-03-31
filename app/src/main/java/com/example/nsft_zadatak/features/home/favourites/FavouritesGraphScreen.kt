package com.example.nsft_zadatak.features.home.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nsft_zadatak.R
import com.example.nsft_zadatak.features.home.RepositoryDetailsRoute
import com.example.nsft_zadatak.features.home.favourites.favourite_contributors.FavouriteContributorsScreen
import com.example.nsft_zadatak.features.home.favourites.favourite_repos.FavouriteRepositoryGraphScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesGraphScreen(modifier: Modifier = Modifier) {
    val favouritesGraphNavController = rememberNavController()
    val favouritesGraphBackStackEntry by favouritesGraphNavController.currentBackStackEntryAsState()
    val tabIndex: Int? = favouritesGraphBackStackEntry?.destination?.getIndexForRoute()

    val favouritesRepositoriesNavController = rememberNavController()
    val favouritesRepositoriesBackStackEntry by favouritesRepositoriesNavController.currentBackStackEntryAsState()
    val isDetailsSelected =
        favouritesRepositoriesBackStackEntry?.destination?.hasRoute<RepositoryDetailsRoute>()
            ?: false
    val isFavouriteRepoGraphSelected =
        favouritesGraphBackStackEntry?.destination?.hasRoute<FavouriteRepositoriesGraphRoute>()
            ?: false

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart)
                { Text("Marko Kostic", textAlign = TextAlign.Left) }
            },
            navigationIcon = {
                if (isDetailsSelected && isFavouriteRepoGraphSelected
                ) IconButton(onClick = { favouritesRepositoriesNavController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            })
        TabRow(selectedTabIndex = tabIndex ?: 0) {
            Tab(selected = tabIndex == 0,
                text = { Text(stringResource(R.string.repositories)) }, onClick = {
                    favouritesGraphNavController.navigate(FavouriteRepositoriesGraphRoute) {
                        popUpTo(favouritesGraphNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            Tab(selected = tabIndex == 1,
                text = { Text(stringResource(R.string.contributors)) }, onClick = {
                    favouritesGraphNavController.navigate(FavouriteContributorsRoute) {
                        popUpTo(favouritesGraphNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }

        NavHost(
            favouritesGraphNavController,
            startDestination = FavouriteRepositoriesGraphRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<FavouriteRepositoriesGraphRoute> {
                FavouriteRepositoryGraphScreen(navController = favouritesRepositoriesNavController)
            }
            composable<FavouriteContributorsRoute> {
                FavouriteContributorsScreen()
            }
        }
    }
}

private fun NavDestination.getIndexForRoute(): Int? {
    return if (hasRoute<FavouriteRepositoriesRoute>())
        0
    else if (hasRoute<FavouriteContributorsRoute>())
        1
    else null
}