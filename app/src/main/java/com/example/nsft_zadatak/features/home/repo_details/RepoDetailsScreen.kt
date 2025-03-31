package com.example.nsft_zadatak.features.home.repo_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nsft_zadatak.R
import com.example.nsft_zadatak.common.ui.ContributorsListItem
import com.example.nsft_zadatak.features.home.repo_details.GitRepositoryContributorsState.GitRepositoryContributorsFailed
import com.example.nsft_zadatak.features.home.repo_details.GitRepositoryContributorsState.GitRepositoryContributorsLoading
import com.example.nsft_zadatak.features.home.repo_details.GitRepositoryContributorsState.GitRepositoryContributorsSuccess
import com.example.nsft_zadatak.features.home.repo_details.GitRepositoryDetailsState.GitRepositoryDetailsFailed
import com.example.nsft_zadatak.features.home.repo_details.GitRepositoryDetailsState.GitRepositoryDetailsLoading
import com.example.nsft_zadatak.features.home.repo_details.GitRepositoryDetailsState.GitRepositoryDetailsSuccess
import com.example.nsft_zadatak.model.GitRepositoryDetailsUiModel

@Composable
fun RepoDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: RepoDetailsViewModel = hiltViewModel(),
) {
    val repoDetails by viewModel.repositoryDetails.collectAsStateWithLifecycle()
    when (val details = repoDetails) {
        is GitRepositoryDetailsFailed -> GitRepositoryDetailsError(onRepositoryRetryClicked = {})
        GitRepositoryDetailsLoading -> GitRepositoryDetailsLoading()
        is GitRepositoryDetailsSuccess -> Column(
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensionResource(R.dimen.spacing_2x))
        ) {
            GitRepositoryDetailsInfo(
                repoDetails = details.repoDetails,
                onFavouriteClicked = viewModel::onChangeRepoFavouriteStatus
            )
            val repoContributors by viewModel.contributors.collectAsStateWithLifecycle()
            GitRepositoryDetailsContributors(
                repoContributors = repoContributors,
                onContributorFavouriteClicked = viewModel::onChangeRepoContributorFavouriteStatus,
                onContributorsRetryClicked = {}
            )
        }
    }
}

@Composable
fun GitRepositoryDetailsError(onRepositoryRetryClicked: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.error_message))
    }
}

@Composable
fun GitRepositoryDetailsLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.loading))
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GitRepositoryDetailsInfo(
    repoDetails: GitRepositoryDetailsUiModel,
    onFavouriteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        GlideImage(
            model = repoDetails.owner.avatarUrl,
            contentDescription = "icon",
            modifier = modifier
                .aspectRatio(ratio = 1f)
                .fillMaxWidth(),
        ) {
            it.error(R.drawable.error).placeholder(R.drawable.image)
                .load(repoDetails.owner.avatarUrl)
        }
        Row(
            modifier = modifier.padding(all = dimensionResource(id = R.dimen.spacing_1x)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = dimensionResource(R.dimen.spacing_1x)),
                text = repoDetails.name,
                textAlign = TextAlign.Left,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = onFavouriteClicked,
                modifier
            ) {
                Icon(
                    if (repoDetails.isFavourite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    stringResource(R.string.favourite)
                )
            }
        }
    }
}

@Composable
fun GitRepositoryDetailsContributors(
    repoContributors: GitRepositoryContributorsState,
    onContributorFavouriteClicked: (Int) -> Unit,
    onContributorsRetryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (repoContributors) {
        is GitRepositoryContributorsFailed -> Text("Contributors Error")
        GitRepositoryContributorsLoading -> Text("Contributors Loading")
        is GitRepositoryContributorsSuccess -> Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            repeat(repoContributors.contributors.size) {
                ContributorsListItem(
                    contributor = repoContributors.contributors[it],
                    onFavouriteClicked = onContributorFavouriteClicked,
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_2x)))
        }
    }
}