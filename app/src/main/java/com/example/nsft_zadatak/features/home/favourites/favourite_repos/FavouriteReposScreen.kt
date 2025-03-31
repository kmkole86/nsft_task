package com.example.nsft_zadatak.features.home.favourites.favourite_repos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nsft_zadatak.R
import com.example.nsft_zadatak.common.ui.RepoListInfoItem
import com.example.nsft_zadatak.common.ui.RepoListItem
import com.example.nsft_zadatak.features.home.RepositoryDetailsRoute
import com.example.nsft_zadatak.features.home.favourites.FavouriteRepositoriesRoute
import com.example.nsft_zadatak.features.home.navigateToRepoDetails
import com.example.nsft_zadatak.features.home.repo_details.RepoDetailsScreen

@Composable
fun FavouriteRepositoryGraphScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            navController,
            startDestination = FavouriteRepositoriesRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<FavouriteRepositoriesRoute> {
                FavouriteReposScreen(onRepoClicked =
                { repoId ->
                    navController.navigateToRepoDetails(
                        repoId = repoId
                    )
                })
            }
            composable<RepositoryDetailsRoute> {
                RepoDetailsScreen()
            }
        }
    }
}


@Composable
fun FavouriteReposScreen(
    modifier: Modifier = Modifier, onRepoClicked: (Int) -> Unit,
    viewModel: FavouriteReposViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        val repositories by viewModel.state.collectAsStateWithLifecycle()
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(bottom = dimensionResource(id = R.dimen.spacing_2x)),
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_2x))
                .weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                repositories.size,
                key = { index -> repositories[index].id },
                itemContent = { index ->
                    RepoListItem(
                        repository = repositories[index],
                        onRepositoryClicked = onRepoClicked,
                        onFavouriteClicked = viewModel::onChangeRepoFavouriteStatus,
                        modifier = modifier
                    )
                })
            if (repositories.isEmpty()) {
                item {
                    RepoListInfoItem(
                        message = "No favourites",
                        modifier = modifier
                    )
                }
            }
        }
    }
}