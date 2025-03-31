package com.example.nsft_zadatak.features.home.favourites.favourite_contributors

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
import com.example.nsft_zadatak.R
import com.example.nsft_zadatak.common.ui.ContributorsListItem
import com.example.nsft_zadatak.common.ui.RepoListInfoItem

@Composable
fun FavouriteContributorsScreen(
    modifier: Modifier = Modifier,
    viewModel: FavouriteContributorsViewModel = hiltViewModel()
) {
    val contributors by viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(bottom = dimensionResource(id = R.dimen.spacing_2x)),
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_2x))
                .weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                contributors.size,
                key = { index -> contributors[index].id },
                itemContent = { index ->
                    ContributorsListItem(
                        contributor = contributors[index],
                        onFavouriteClicked = viewModel::onChangeRepoContributorFavouriteStatus,
                        modifier = modifier
                    )
                })
            if (contributors.isEmpty()) {
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