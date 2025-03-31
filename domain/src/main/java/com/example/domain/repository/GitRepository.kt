package com.example.domain.repository

import com.example.domain.entity.FavouriteStatusResult
import com.example.domain.entity.FetchContributorsResult
import com.example.domain.entity.FetchRepoDetailsResult
import com.example.domain.entity.FetchRepoPageResult
import com.example.domain.entity.GitRepositoryDetails
import com.example.domain.entity.GitRepositoryEntity
import com.example.domain.entity.GitRepositoryContributor
import kotlinx.coroutines.flow.Flow

interface GitRepository {

    //repo list
    fun observeCachedRepositories(): Flow<List<GitRepositoryEntity>>

    fun fetchRepositoryPage(pageCursor: Int): Flow<FetchRepoPageResult>

    //repo favourite
    fun changeRepoFavouriteStatus(repoId: Int): Flow<FavouriteStatusResult>

    fun observeFavouriteRepositories(): Flow<List<GitRepositoryEntity>>

    //repo details
    fun observeRepositoryDetails(repoId: Int): Flow<GitRepositoryDetails?>

    fun fetchRepositoryDetails(repoId: Int): Flow<FetchRepoDetailsResult>

    //repo contributors
    fun observeRepositoryContributors(repoId: Int): Flow<List<GitRepositoryContributor>>

    fun fetchRepositoryContributors(repoId: Int): Flow<FetchContributorsResult>

    //repo contributors favourite
    fun changeRepoContributorsFavouriteStatus(contributorId: Int): Flow<FavouriteStatusResult>

    fun observeFavouriteRepositoryContributors(): Flow<List<GitRepositoryContributor>>
}