package com.example.data.repository_impl

import com.example.data.api.common.mapToLocal
import com.example.data.api.common.mapToLocalContributors
import com.example.data.api.data_source_impl.GitRepoRemoteDataSource
import com.example.data.common.mapToDomain
import com.example.data.common.mapToDomainContributors
import com.example.data.common.mapToDomainRepositories
import com.example.data.database.data_source_impl.GitRepoLocalDataSource
import com.example.domain.entity.FavouriteStatusResult
import com.example.domain.entity.FetchContributorsResult
import com.example.domain.entity.FetchRepoContributorsError
import com.example.domain.entity.FetchRepoDetailsResult
import com.example.domain.entity.FetchRepoPageResult
import com.example.domain.entity.GitRepositoryContributor
import com.example.domain.entity.GitRepositoryDetails
import com.example.domain.entity.RepoDetailsError
import com.example.domain.entity.RepoPageError
import com.example.domain.repository.GitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class GitRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val localDatasource: GitRepoLocalDataSource,
    private val remoteDataSource: GitRepoRemoteDataSource
) : GitRepository {

    override fun observeCachedRepositories(): Flow<List<com.example.domain.entity.GitRepositoryEntity>> =
        localDatasource.observeListRepositories().map { it.mapToDomainRepositories() }

    override fun fetchRepositoryPage(pageCursor: Int): Flow<FetchRepoPageResult> =
        flow<FetchRepoPageResult> {
            if (pageCursor == 0) localDatasource.clearRepoListIndex()
            remoteDataSource.fetchRepos(pageCursor = pageCursor).onSuccess {
                with(it.items) {
                    localDatasource.cacheRepos(map { item -> item.mapToLocal() })
                    localDatasource.updateReposListIndex(map { item -> item.mapToLocal() })
                }
                //TODO implement reach end of list,
                //due to possible incomplete results etc
                emit(
                    FetchRepoPageResult.FetchRepoPageSuccess(
                        nextPageCursor = pageCursor + 1
                    )
                )
            }.onFailure {
                emit(
                    FetchRepoPageResult.FetchRepoPageFailed(
                        error = RepoPageError.GenericError, pageCursor = pageCursor
                    )
                )
            }
        }.onStart { emit(FetchRepoPageResult.FetchRepoPageLoading(pageCursor = pageCursor)) }
            .flowOn(dispatcher)

    override fun changeRepoFavouriteStatus(repoId: Int): Flow<FavouriteStatusResult> =
        flow<FavouriteStatusResult> {
            emit(
                FavouriteStatusResult.FavouriteStatusSuccess(
                    localDatasource.changeRepoFavouriteStatus(
                        repoId
                    )
                )
            )
        }.onStart { emit(FavouriteStatusResult.FavouriteStatusLoading) }.flowOn(dispatcher)

    override fun observeFavouriteRepositories(): Flow<List<com.example.domain.entity.GitRepositoryEntity>> =
        localDatasource.observeFavouriteRepositories().map { it.mapToDomainRepositories() }

    //repo details
    override fun fetchRepositoryDetails(repoId: Int): Flow<FetchRepoDetailsResult> =
        flow<FetchRepoDetailsResult> {
            val repository = localDatasource.getRepository(repoId)

            remoteDataSource.fetchRepoDetails(
                repositoryOwnerName = repository.owner.userName,
                repositoryName = repository.name
            ).onSuccess {
                localDatasource.cacheRepoDetails(it.mapToLocal())
                emit(
                    FetchRepoDetailsResult.FetchRepoDetailsSuccess
                )
            }.onFailure {
                emit(
                    FetchRepoDetailsResult.FetchRepoDetailsFailed(
                        error = RepoDetailsError.GenericError
                    )
                )
            }
        }.onStart { emit(FetchRepoDetailsResult.FetchRepoDetailsLoading) }.flowOn(dispatcher)

    override fun observeRepositoryDetails(repoId: Int): Flow<GitRepositoryDetails?> =
        localDatasource.observeRepositoryDetails(repoId).distinctUntilChanged()
            .map { it?.mapToDomain() }

    //contributors
    override fun observeRepositoryContributors(repoId: Int): Flow<List<GitRepositoryContributor>> =
        localDatasource.observeRepositoryContributors(repoId).map { it.mapToDomainContributors() }

    override fun fetchRepositoryContributors(repoId: Int): Flow<FetchContributorsResult> =
        flow<FetchContributorsResult> {
            val repository = localDatasource.getRepository(repoId)
            //TODO implement fetch if not present in db or throw ex
            //adhoc assume its always there
            remoteDataSource.fetchRepoContributors(
                url = repository.contributorsUrl
            ).onSuccess {
                localDatasource.cacheRepositoryContributors(
                    repoId = repoId,
                    contributors = it.mapToLocalContributors()
                )
                emit(
                    FetchContributorsResult.FetchContributorsSuccess
                )
            }.onFailure {
                emit(
                    FetchContributorsResult.FetchContributorsFailed(
                        error = FetchRepoContributorsError.GenericError
                    )
                )
            }
        }.onStart { emit(FetchContributorsResult.FetchContributorsLoading) }.flowOn(dispatcher)

    override fun changeRepoContributorsFavouriteStatus(contributorId: Int): Flow<FavouriteStatusResult> =
        flow<FavouriteStatusResult> {
            emit(
                FavouriteStatusResult.FavouriteStatusSuccess(
                    localDatasource.changeRepoContributorFavouriteStatus(
                        contributorId
                    )
                )
            )
        }.onStart { emit(FavouriteStatusResult.FavouriteStatusLoading) }.flowOn(dispatcher)

    override fun observeFavouriteRepositoryContributors(): Flow<List<GitRepositoryContributor>> =
        localDatasource.observeFavouriteRepositoryContributors()
            .map { it.mapToDomainContributors() }
}