package com.example.domain.entity

sealed class FetchContributorsResult {
    object FetchContributorsLoading : FetchContributorsResult()
    object FetchContributorsSuccess :
        FetchContributorsResult()

    data class FetchContributorsFailed(val error: FetchRepoContributorsError) :
        FetchContributorsResult()
}

sealed class FetchRepoContributorsError {
    object GenericError : FetchRepoContributorsError()
    data class ApiError(val errorCode: Int? = null) : FetchRepoContributorsError()
}