package com.example.domain.entity

sealed class FetchRepoDetailsResult {
    object FetchRepoDetailsLoading : FetchRepoDetailsResult()
    object FetchRepoDetailsSuccess : FetchRepoDetailsResult()
    data class FetchRepoDetailsFailed(val error: RepoDetailsError) : FetchRepoDetailsResult()
}

sealed class RepoDetailsError {
    object GenericError : RepoDetailsError()
    data class ApiError(val errorCode: Int? = null) : RepoDetailsError()
}