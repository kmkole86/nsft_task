package com.example.data.api.data_source_impl

import com.example.data.api.common.ApiConstants.CURSOR_PARAM_KEY
import com.example.data.api.common.ApiConstants.DEFAULT_PAGE_SIZE
import com.example.data.api.common.ApiConstants.PAGE_SIZE_PARAM_KEY
import com.example.data.api.common.ApiConstants.QUERY_PARAM_KEY
import com.example.data.api.common.ApiConstants.QUERY_PARAM_ORDER_KEY
import com.example.data.api.common.ApiConstants.QUERY_PARAM_SORT_KEY
import com.example.data.api.model.GitRepoContributorResponse
import com.example.data.api.model.GitRepoPageResponse
import com.example.data.api.model.GitRepositoryDetailsResponse
import com.example.data.common.runCatchingCancelable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

interface GitRepoRemoteDataSource {

    suspend fun fetchRepos(pageCursor: Int?): Result<GitRepoPageResponse>

    suspend fun fetchRepoDetails(
        repositoryOwnerName: String,
        repositoryName: String
    ): Result<GitRepositoryDetailsResponse>

    suspend fun fetchRepoContributors(url: String): Result<List<GitRepoContributorResponse>>
}

class GitRepoRemoteDataSourceImpl @Inject constructor(private val client: HttpClient) :
    GitRepoRemoteDataSource {

    override suspend fun fetchRepos(
        pageCursor: Int?
    ): Result<GitRepoPageResponse> =
        runCatchingCancelable {
            client.get("https://api.github.com/search/repositories") {
                url {
                    parameters.append(QUERY_PARAM_KEY, "language:kotlin")
                    parameters.append(QUERY_PARAM_ORDER_KEY, "desc")
                    parameters.append(QUERY_PARAM_SORT_KEY, "stars")
                    parameters.append(PAGE_SIZE_PARAM_KEY, DEFAULT_PAGE_SIZE.toString())
                    parameters.append(CURSOR_PARAM_KEY, pageCursor.toString())
                }
            }.body<GitRepoPageResponse>()
        }

    override suspend fun fetchRepoDetails(
        repositoryOwnerName: String,
        repositoryName: String
    ): Result<GitRepositoryDetailsResponse> =
        runCatchingCancelable {
            client.get(urlString = "https://api.github.com/repos/${repositoryOwnerName}/${repositoryName}") {

            }.body<GitRepositoryDetailsResponse>()
        }

    override suspend fun fetchRepoContributors(url: String): Result<List<GitRepoContributorResponse>> =
        runCatchingCancelable {
            client.get(urlString = url).body<List<GitRepoContributorResponse>>()
        }
}