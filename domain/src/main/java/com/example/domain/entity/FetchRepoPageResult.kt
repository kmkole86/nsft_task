package com.example.domain.entity

//TODO make to ListResult using <T, E>
sealed class FetchRepoPageResult {

//    abstract val nextPageCursor: Int?

    data class FetchRepoPageLoading(val pageCursor: Int) : FetchRepoPageResult()

    data class FetchRepoPageSuccess(
        val nextPageCursor: Int?
    ) : FetchRepoPageResult()

    data class FetchRepoPageFailed(
        val error: RepoPageError, val pageCursor: Int,
    ) : FetchRepoPageResult()
}

sealed class RepoPageError {
    object GenericError : RepoPageError()
    data class ApiError(val errorCode: Int? = null) : RepoPageError()
}