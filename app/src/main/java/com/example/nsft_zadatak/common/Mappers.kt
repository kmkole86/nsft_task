package com.example.nsft_zadatak.common

import com.example.domain.entity.GitRepositoryEntity
import com.example.domain.entity.GitRepositoryContributor
import com.example.domain.entity.GitRepositoryDetails
import com.example.domain.entity.GitRepositoryOwner
import com.example.nsft_zadatak.model.GitRepositoryContributorUiModel
import com.example.nsft_zadatak.model.GitRepositoryDetailsUiModel
import com.example.nsft_zadatak.model.GitRepositoryOwnerUiModel
import com.example.nsft_zadatak.model.GitRepositoryUiModel

fun List<GitRepositoryEntity>.mapToPresentationRepositories() =
    map { it.mapToPresentation() }

fun GitRepositoryEntity.mapToPresentation() =
    GitRepositoryUiModel(
        id = id,
        name = name,
        description = description,
        programmingLanguage = programmingLanguage,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        openIssues = openIssues,
        watchersCount = watchersCount,
        owner = owner.mapToPresentation(),
        contributorsUrl = contributorsUrl,
        isFavourite = isFavourite
    )

fun GitRepositoryOwner.mapToPresentation() =
    GitRepositoryOwnerUiModel(
        userName = userName,
        avatarUrl = avatarUrl
    )

fun GitRepositoryDetails.mapToPresentation() =
    GitRepositoryDetailsUiModel(
        id = id,
        name = name,
        description = description,
        programmingLanguage = programmingLanguage,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        openIssues = openIssues,
        watchersCount = watchersCount,
        owner = owner.mapToPresentation(),
        htmlUrl = htmlUrl,
        contributorsUrl = contributorsUrl,
        defaultBranch = defaultBranch,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isFavourite = isFavourite,
    )

fun List<GitRepositoryContributor>.mapToPresentationContributors() =
    map { it.mapToPresentation() }

fun GitRepositoryContributor.mapToPresentation() =
    GitRepositoryContributorUiModel(
        isFavourite = isFavourite,
        id = id,
        name = name,
        avatarUrl = avatarUrl,
    )