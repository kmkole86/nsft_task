package com.example.nsft_zadatak.features.home.repo_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.data.repository_impl.GitRepositoryImpl
import com.example.domain.entity.FetchContributorsResult
import com.example.domain.entity.FetchRepoDetailsResult
import com.example.domain.entity.GitRepositoryContributor
import com.example.domain.entity.GitRepositoryDetails
import com.example.nsft_zadatak.common.mapToPresentation
import com.example.nsft_zadatak.common.mapToPresentationContributors
import com.example.nsft_zadatak.features.home.RepositoryDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gitRepository: GitRepositoryImpl,
) : ViewModel() {

    private val repoId = savedStateHandle.toRoute<RepositoryDetailsRoute>().repoId

    private val _fetchRepoDetails: MutableStateFlow<FetchRepoDetailsResult> =
        MutableStateFlow(FetchRepoDetailsResult.FetchRepoDetailsLoading)

    val repositoryDetails: StateFlow<GitRepositoryDetailsState> =
        combine<FetchRepoDetailsResult, GitRepositoryDetails?, GitRepositoryDetailsState>(
            flow = _fetchRepoDetails,
            flow2 = gitRepository.observeRepositoryDetails(repoId),
        ) { fetchRepoStatus, repoDetails ->
            when (fetchRepoStatus) {
                is FetchRepoDetailsResult.FetchRepoDetailsFailed -> GitRepositoryDetailsState.GitRepositoryDetailsFailed(
                    error = fetchRepoStatus.error
                )

                FetchRepoDetailsResult.FetchRepoDetailsLoading -> GitRepositoryDetailsState.GitRepositoryDetailsLoading
                FetchRepoDetailsResult.FetchRepoDetailsSuccess -> if (repoDetails != null)
                    GitRepositoryDetailsState.GitRepositoryDetailsSuccess(
                        repoDetails.mapToPresentation()
                    ) else GitRepositoryDetailsState.GitRepositoryDetailsLoading
            }
        }.onStart { fetchRepoDetails(repoId) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GitRepositoryDetailsState.GitRepositoryDetailsLoading
        )

    private val _fetchContributorsResult: MutableStateFlow<FetchContributorsResult> =
        MutableStateFlow(FetchContributorsResult.FetchContributorsLoading)

    val contributors: StateFlow<GitRepositoryContributorsState> =
        combine<FetchContributorsResult, List<GitRepositoryContributor>, GitRepositoryContributorsState>(
            flow = _fetchContributorsResult,
            flow2 = gitRepository.observeRepositoryContributors(repoId),
        ) { fetchContributorsStatus, contributors ->
            when (fetchContributorsStatus) {
                is FetchContributorsResult.FetchContributorsFailed -> GitRepositoryContributorsState.GitRepositoryContributorsFailed(
                    error = fetchContributorsStatus.error
                )

                FetchContributorsResult.FetchContributorsLoading -> GitRepositoryContributorsState.GitRepositoryContributorsLoading
                is FetchContributorsResult.FetchContributorsSuccess -> GitRepositoryContributorsState.GitRepositoryContributorsSuccess(
                    contributors = contributors.mapToPresentationContributors()
                )
            }

        }.onStart { fetchContributors(repoId) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GitRepositoryContributorsState.GitRepositoryContributorsLoading
        )

    fun onChangeRepoFavouriteStatus() {
        gitRepository.changeRepoFavouriteStatus(
            repoId
        ).onEach {
            //handle error here, emit toast...
            //if there is a real api call
            //for local impl we are using there is no error possible
        }.launchIn(viewModelScope)
    }

    fun onChangeRepoContributorFavouriteStatus(contributorId: Int) {
        gitRepository.changeRepoContributorsFavouriteStatus(
            contributorId
        ).onEach {
            //handle error here, emit toast...
            //if there is a real api call
            //for local impl we are using there is no error possible
        }.launchIn(viewModelScope)
    }

    fun onRetryRepositoryFetch() {
        fetchRepoDetails(repoId = repoId)
    }

    private fun fetchRepoDetails(repoId: Int) {
        gitRepository.fetchRepositoryDetails(repoId = repoId)
            .onEach {
                _fetchRepoDetails.value = it
            }
            .launchIn(
                scope = viewModelScope,
            )
    }

    fun onRetryContributorsFetch() {
        fetchContributors(repoId = repoId)
    }

    private fun fetchContributors(repoId: Int) {
        gitRepository.fetchRepositoryContributors(repoId = repoId)
            .onEach {
                _fetchContributorsResult.value = it
            }
            .launchIn(
                scope = viewModelScope,
            )
    }
}