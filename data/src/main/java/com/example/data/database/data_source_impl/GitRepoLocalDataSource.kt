package com.example.data.database.data_source_impl

import com.example.data.database.common.mapToContributorsDb
import com.example.data.database.common.mapToContributorsLocal
import com.example.data.database.common.mapToDb
import com.example.data.database.common.mapToLocal
import com.example.data.database.common.mapToRepositoriesLocal
import com.example.data.database.dao.GitRepoDao
import com.example.data.database.model.GitRepositoryContributorFavouriteIdDb
import com.example.data.database.model.GitRepositoryDbListIndex
import com.example.data.database.model.GitRepositoryFavouriteIdDb
import com.example.data.model.GitRepositoryContributorLocal
import com.example.data.model.GitRepositoryContributorLocalWithFavourite
import com.example.data.model.GitRepositoryDetailsLocal
import com.example.data.model.GitRepositoryDetailsLocalWithFavourite
import com.example.data.model.GitRepositoryLocal
import com.example.data.model.GitRepositoryLocalWithFavourite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GitRepoLocalDataSource {
    //list
    suspend fun cacheRepos(repos: List<GitRepositoryLocal>)

    fun observeListRepositories(): Flow<List<GitRepositoryLocalWithFavourite>>

    suspend fun updateReposListIndex(repos: List<GitRepositoryLocal>)

    suspend fun clearRepoListIndex()

    suspend fun getRepository(repoId: Int): GitRepositoryLocal

    //details
    suspend fun cacheRepoDetails(repo: GitRepositoryDetailsLocal)

    fun observeRepositoryDetails(repoId: Int): Flow<GitRepositoryDetailsLocalWithFavourite?>

    //details favourites
    fun observeFavouriteRepositories(): Flow<List<GitRepositoryLocalWithFavourite>>

    suspend fun changeRepoFavouriteStatus(repoId: Int): Boolean

    //contributors

    suspend fun cacheRepositoryContributors(
        repoId: Int,
        contributors: List<GitRepositoryContributorLocal>
    )

    fun observeRepositoryContributors(repoId: Int): Flow<List<GitRepositoryContributorLocalWithFavourite>>

    //contributors favourites
    fun observeFavouriteRepositoryContributors(): Flow<List<GitRepositoryContributorLocalWithFavourite>>

    suspend fun changeRepoContributorFavouriteStatus(contributorId: Int): Boolean
}

class GitRepoLocalDataSourceImpl @Inject constructor(private val dao: GitRepoDao) :
    GitRepoLocalDataSource {

    override fun observeListRepositories(): Flow<List<GitRepositoryLocalWithFavourite>> =
        dao.observeRepositories().distinctUntilChanged().map { it.mapToRepositoriesLocal() }

    override suspend fun clearRepoListIndex() {
        dao.clearRepositoryIndex()
    }

    override suspend fun getRepository(repoId: Int): GitRepositoryLocal =
        dao.getRepository(repoId).mapToLocal()

    override suspend fun cacheRepos(repos: List<GitRepositoryLocal>) {
        dao.insertRepositories(repos.map { it.mapToDb() })
    }

    override suspend fun updateReposListIndex(repos: List<GitRepositoryLocal>) {
        dao.insertRepositoryIndex(repos.map { GitRepositoryDbListIndex(id = it.id) })
    }

    override suspend fun changeRepoFavouriteStatus(repoId: Int): Boolean {
        if (dao.getRepositoryFavouriteStatus(repoId = repoId))
            dao.deleteFavouriteRepositoryId(repoId)
        else
            dao.insertFavouriteRepositoryId(GitRepositoryFavouriteIdDb(repoId))

        return dao.getRepositoryFavouriteStatus(repoId = repoId)
    }

    override suspend fun cacheRepositoryContributors(
        repoId: Int,
        contributors: List<GitRepositoryContributorLocal>
    ) {
        dao.insertContributors(repoId = repoId, contributors = contributors.mapToContributorsDb())
    }

    override fun observeRepositoryContributors(repoId: Int): Flow<List<GitRepositoryContributorLocalWithFavourite>> =
        dao.observeRepositoryContributors(repoId).distinctUntilChanged()
            .map { it.mapToContributorsLocal() }


    override fun observeFavouriteRepositoryContributors(): Flow<List<GitRepositoryContributorLocalWithFavourite>> =
        dao.observeFavouriteContributors().distinctUntilChanged()
            .map { it.mapToContributorsLocal() }

    override suspend fun changeRepoContributorFavouriteStatus(contributorId: Int): Boolean {
        if (dao.getRepositoryContributorFavouriteStatus(contributorId = contributorId))
            dao.deleteFavouriteContributorId(contributorId)
        else
            dao.insertFavouriteContributorId(GitRepositoryContributorFavouriteIdDb(contributorId))

        return dao.getRepositoryFavouriteStatus(contributorId)
    }

    override fun observeFavouriteRepositories(): Flow<List<GitRepositoryLocalWithFavourite>> =
        dao.observeFavouriteRepositories().distinctUntilChanged()
            .map { it.mapToRepositoriesLocal() }

    override suspend fun cacheRepoDetails(repo: GitRepositoryDetailsLocal) {
        dao.insertRepositoryDetails(repo.mapToDb())
    }

    override fun observeRepositoryDetails(repoId: Int): Flow<GitRepositoryDetailsLocalWithFavourite?> =
        dao.observeRepositoryDetails(repoId).distinctUntilChanged().map { it?.mapToLocal() }
}