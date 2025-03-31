package com.example.data.common

import com.example.data.model.GitRepositoryContributorLocalWithFavourite
import com.example.data.model.GitRepositoryDetailsLocalWithFavourite
import com.example.data.model.GitRepositoryLocalWithFavourite
import com.example.data.model.OwnerLocal
import com.example.domain.entity.GitRepositoryContributor
import com.example.domain.entity.GitRepositoryDetails
import com.example.domain.entity.GitRepositoryEntity
import com.example.domain.entity.GitRepositoryOwner

fun List<GitRepositoryLocalWithFavourite>.mapToDomainRepositories() =
    map { it.mapToDomain() }

fun GitRepositoryLocalWithFavourite.mapToDomain() =
    GitRepositoryEntity(
        id = repository.id,
        name = repository.name,
        description = repository.description,
        programmingLanguage = repository.programmingLanguage,
        stargazersCount = repository.stargazersCount,
        forksCount = repository.forksCount,
        openIssues = repository.openIssues,
        watchersCount = repository.watchersCount,
        owner = repository.owner.mapToDomain(),
        contributorsUrl = repository.contributorsUrl,
        isFavourite = isFavourite
    )

fun OwnerLocal.mapToDomain() =
    GitRepositoryOwner(
        userName = userName,
        avatarUrl = avatarUrl
    )

fun GitRepositoryDetailsLocalWithFavourite.mapToDomain() =
    GitRepositoryDetails(
        id = repository.id,
        name = repository.name,
        description = repository.description,
        programmingLanguage = repository.programmingLanguage,
        stargazersCount = repository.stargazersCount,
        forksCount = repository.forksCount,
        openIssues = repository.openIssues,
        watchersCount = repository.watchersCount,
        owner = repository.owner.mapToDomain(),
        htmlUrl = repository.htmlUrl,
        contributorsUrl = repository.contributorsUrl,
        defaultBranch = repository.defaultBranch,
        createdAt = repository.createdAt,
        updatedAt = repository.updatedAt,
        isFavourite = isFavourite,
    )

fun List<GitRepositoryContributorLocalWithFavourite>.mapToDomainContributors() =
    map { it.mapToDomain() }

fun GitRepositoryContributorLocalWithFavourite.mapToDomain() =
    GitRepositoryContributor(

        isFavourite = isFavourite,
        id = contributor.id,
        name = contributor.name,
        avatarUrl = contributor.avatarUrl,
    )