package com.example.nsft_zadatak.features.home.repo_details

import androidx.compose.runtime.Stable
import com.example.domain.entity.FetchRepoContributorsError
import com.example.domain.entity.GitRepositoryContributor
import com.example.domain.entity.RepoDetailsError
import com.example.nsft_zadatak.model.GitRepositoryContributorUiModel
import com.example.nsft_zadatak.model.GitRepositoryDetailsUiModel

@Stable
sealed class GitRepositoryDetailsState {
    object GitRepositoryDetailsLoading : GitRepositoryDetailsState()
    data class GitRepositoryDetailsSuccess(val repoDetails: GitRepositoryDetailsUiModel) :
        GitRepositoryDetailsState()

    data class GitRepositoryDetailsFailed(val error: RepoDetailsError) : GitRepositoryDetailsState()
}

@Stable
sealed class GitRepositoryContributorsState {
    object GitRepositoryContributorsLoading : GitRepositoryContributorsState()
    data class GitRepositoryContributorsSuccess(val contributors: List<GitRepositoryContributorUiModel>) :
        GitRepositoryContributorsState()

    data class GitRepositoryContributorsFailed(val error: FetchRepoContributorsError) :
        GitRepositoryContributorsState()
}