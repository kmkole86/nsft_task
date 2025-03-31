package com.example.nsft_zadatak.features.home.repos_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository_impl.GitRepositoryImpl
import com.example.domain.entity.FetchRepoPageResult
import com.example.domain.entity.GitRepositoryEntity
import com.example.domain.entity.RepoPageError
import com.example.nsft_zadatak.common.mapToPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ReposListViewModel @Inject constructor(
    private val gitRepository: GitRepositoryImpl,
) : ViewModel() {

    private val _query: MutableStateFlow<String> =
        MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _fetchStatus: MutableStateFlow<FetchNewRepoPageStatus> =
        MutableStateFlow(FetchNewRepoPageStatus.FetchNewRepoPageStatusIdle)

    val state: StateFlow<ReposListState> =
        combine<String, FetchNewRepoPageStatus, List<GitRepositoryEntity>, ReposListState>(
            flow = _query.debounce(300).distinctUntilChanged(),
            flow2 = _fetchStatus,
            flow3 = gitRepository.observeCachedRepositories()
        ) { searchQuery, fetchStatus, repositories ->

            when (fetchStatus) {
                FetchNewRepoPageStatus.FetchNewRepoPageStatusIdle -> ReposListState.ReposListStateLoading(
                    repos = persistentListOf(),
                    nextPageCursor = 0
                )

                is FetchNewRepoPageStatus.FetchNewRepoPageStatusLoading -> ReposListState.ReposListStateLoading(
                    repos = repositories.filter { it.name.contains(searchQuery) }
                        .map { it.mapToPresentation() }.toImmutableList(),
                    nextPageCursor = fetchStatus.pageCursor
                )

                is FetchNewRepoPageStatus.FetchNewRepoPageStatusSuccess -> ReposListState.ReposListStateLoaded(
                    repos = repositories.filter { it.name.contains(searchQuery) }
                        .map { it.mapToPresentation() }.toImmutableList(),
                    nextPageCursor = fetchStatus.nextPageCursor
                )

                is FetchNewRepoPageStatus.FetchNewRepoPageStatusFailed -> ReposListState.ReposListStateFailed(
                    repos = repositories.filter { it.name.contains(searchQuery) }
                        .map { it.mapToPresentation() }.toImmutableList(),
                    nextPageCursor = fetchStatus.nextCursor,
                    error = fetchStatus.error
                )
            }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ReposListState.empty()
        )

    fun loadNextPage() {
        if (_query.value.isEmpty() && state.value.canLoadMore) {
            gitRepository.fetchRepositoryPage(pageCursor = (state.value.nextPageCursor ?: 0) + 1)
                .onEach { result ->
                    _fetchStatus.value = result.reduce()
                }.launchIn(scope = viewModelScope)
        }
    }

    fun onQueryChanged(query: String) {
        _query.value = query
    }

    fun onRetry() {
        loadNextPage()
    }

    fun onChangeRepoFavouriteStatus(repoId: Int) {
        gitRepository.changeRepoFavouriteStatus(
            repoId
        ).onEach {
            //handle error here, emit toast...
            //if there is a real api call
            //for local impl we are using there is no error possible
        }.launchIn(viewModelScope)
    }
}

sealed class FetchNewRepoPageStatus {

    object FetchNewRepoPageStatusIdle : FetchNewRepoPageStatus()
    data class FetchNewRepoPageStatusLoading(val pageCursor: Int) : FetchNewRepoPageStatus()
    data class FetchNewRepoPageStatusSuccess(val nextPageCursor: Int?) :
        FetchNewRepoPageStatus()

    data class FetchNewRepoPageStatusFailed(val error: RepoPageError, val nextCursor: Int?) :
        FetchNewRepoPageStatus()
}

private fun FetchRepoPageResult.reduce(): FetchNewRepoPageStatus {
    return when (this) {
        is FetchRepoPageResult.FetchRepoPageFailed -> FetchNewRepoPageStatus.FetchNewRepoPageStatusFailed(
            error = error, nextCursor = pageCursor
        )

        is FetchRepoPageResult.FetchRepoPageSuccess -> FetchNewRepoPageStatus.FetchNewRepoPageStatusSuccess(
            nextPageCursor
        )

        is FetchRepoPageResult.FetchRepoPageLoading -> FetchNewRepoPageStatus.FetchNewRepoPageStatusLoading(
            pageCursor = pageCursor
        )
    }
}