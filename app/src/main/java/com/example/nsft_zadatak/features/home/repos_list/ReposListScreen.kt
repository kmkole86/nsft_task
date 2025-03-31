package com.example.nsft_zadatak.features.home.repos_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nsft_zadatak.R
import com.example.nsft_zadatak.common.ui.OnBottomReached
import com.example.nsft_zadatak.common.ui.RepoListErrorItem
import com.example.nsft_zadatak.common.ui.RepoListInfoItem
import com.example.nsft_zadatak.common.ui.RepoListItem
import com.example.nsft_zadatak.common.ui.RepoListLoadingItem
import com.example.nsft_zadatak.features.home.RepositoryDetailsRoute
import com.example.nsft_zadatak.features.home.navigateToRepoDetails
import com.example.nsft_zadatak.features.home.repo_details.RepoDetailsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReposListGraphScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val isDetailsSelected = backStackEntry?.destination?.hasRoute<RepositoryDetailsRoute>()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart)
                { Text("Marko Kostic", textAlign = TextAlign.Left) }
            },
            navigationIcon = {
                if (isDetailsSelected == true
                ) IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            })
        NavHost(
            navController,
            startDestination = ReposListRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<ReposListRoute> {
                ReposListScreen(onRepoClicked = { repoId ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReposListScreen(
    modifier: Modifier = Modifier, onRepoClicked: (Int) -> Unit,
    viewModel: ReposListViewModel = hiltViewModel(),
) {

    val lazyListState = rememberLazyListState()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val repositories by viewModel.state.collectAsStateWithLifecycle()

    lazyListState.OnBottomReached {
        viewModel::loadNextPage.invoke()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onQueryChanged,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_2x))
        )
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = dimensionResource(id = R.dimen.spacing_2x),
                top = dimensionResource(id = R.dimen.spacing_2x)
            ),
            state = lazyListState, modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_2x))
                .weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                repositories.repos.size,
                key = { index -> repositories.repos[index].id },
                itemContent = { index ->
                    RepoListItem(
                        repository = repositories.repos[index],
                        onRepositoryClicked = onRepoClicked,
                        onFavouriteClicked = viewModel::onChangeRepoFavouriteStatus,
                        modifier = modifier
                    )
                })
            when (repositories) {
                is ReposListState.ReposListStateFailed -> {
                    item {
                        RepoListErrorItem(
                            onRetryClicked = viewModel::onRetry,
                            modifier = modifier
                        )
                    }
                }

                is ReposListState.ReposListStateLoaded -> {
                    if (repositories.repos.isEmpty() && query.isNotEmpty()) {
                        item {
                            RepoListInfoItem(
                                message = "No result",
                                modifier = modifier
                            )
                        }
                    } else if (repositories.hasNextPage && query.isEmpty()) {
                        item {
                            RepoListLoadingItem(modifier = modifier)
                        }
                    }
                }

                is ReposListState.ReposListStateLoading -> {
                    item {
                        RepoListLoadingItem(modifier = modifier)
                    }
                }
            }
        }
    }
}