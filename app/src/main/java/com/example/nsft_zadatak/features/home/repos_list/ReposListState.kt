package com.example.nsft_zadatak.features.home.repos_list

import androidx.compose.runtime.Stable
import com.example.domain.entity.RepoPageError
import com.example.nsft_zadatak.model.GitRepositoryUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
sealed class ReposListState {

    companion object {
        fun empty() = ReposListStateLoaded(repos = persistentListOf(), nextPageCursor = 0)
    }

    val hasNextPage: Boolean
        get() = nextPageCursor != null

    abstract val repos: ImmutableList<GitRepositoryUiModel>
    abstract val nextPageCursor: Int?

    data class ReposListStateLoading(
        override val repos: ImmutableList<GitRepositoryUiModel>,
        override val nextPageCursor: Int?,
    ) : ReposListState()

    data class ReposListStateFailed(
        override val repos: ImmutableList<GitRepositoryUiModel>,
        override val nextPageCursor: Int?,
        val error: RepoPageError?
    ) : ReposListState()

    data class ReposListStateLoaded(
        override val repos: ImmutableList<GitRepositoryUiModel>,
        override val nextPageCursor: Int?,
    ) : ReposListState()
}

val ReposListState.canLoadMore: Boolean
    get() = nextPageCursor != null && (this !is ReposListState.ReposListStateFailed && this !is ReposListState.ReposListStateLoading)