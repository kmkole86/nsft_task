package com.example.data.api.common

import com.example.data.api.model.GitRepoContributorResponse
import com.example.data.api.model.GitRepositoryDetailsResponse
import com.example.data.api.model.GitRepositoryResponse
import com.example.data.api.model.OwnerResponse
import com.example.data.model.GitRepositoryContributorLocal
import com.example.data.model.GitRepositoryDetailsLocal
import com.example.data.model.GitRepositoryLocal
import com.example.data.model.OwnerLocal

fun List<GitRepositoryResponse>.mapToLocalRepositories() =
    map { it.mapToLocal() }

fun GitRepositoryResponse.mapToLocal() =
    GitRepositoryLocal(
        id = id,
        name = name,
        description = description,
        programmingLanguage = programmingLanguage,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        openIssues = openIssues,
        watchersCount = watchersCount,
        contributorsUrl = contributorsUrl,
        owner = owner.mapToLocal()
    )

fun OwnerResponse.mapToLocal() =
    OwnerLocal(
        userName = userName,
        avatarUrl = avatarUrl
    )

fun GitRepositoryDetailsResponse.mapToLocal() =
    GitRepositoryDetailsLocal(
        id = id,
        name = name,
        description = description,
        programmingLanguage = programmingLanguage,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        openIssues = openIssues,
        watchersCount = watchersCount,
        owner = owner.mapToLocal(),
        htmlUrl = htmlUrl,
        contributorsUrl = contributorsUrl,
        defaultBranch = defaultBranch,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun List<GitRepoContributorResponse>.mapToLocalContributors() =
    map { it.mapToLocal() }

fun GitRepoContributorResponse.mapToLocal() =
    GitRepositoryContributorLocal(
        id = id,
        name = name,
        avatarUrl = avatarUrl,
    )