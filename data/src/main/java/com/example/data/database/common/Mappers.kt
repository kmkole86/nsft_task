package com.example.data.database.common

import com.example.data.database.model.GitRepositoryContributorDb
import com.example.data.database.model.GitRepositoryContributorDbWithFavourite
import com.example.data.database.model.GitRepositoryDb
import com.example.data.database.model.GitRepositoryDbWithFavourite
import com.example.data.database.model.GitRepositoryDetailsDb
import com.example.data.database.model.GitRepositoryDetailsDbWithFavourite
import com.example.data.database.model.OwnerDb
import com.example.data.model.GitRepositoryContributorLocal
import com.example.data.model.GitRepositoryContributorLocalWithFavourite
import com.example.data.model.GitRepositoryDetailsLocal
import com.example.data.model.GitRepositoryDetailsLocalWithFavourite
import com.example.data.model.GitRepositoryLocal
import com.example.data.model.GitRepositoryLocalWithFavourite
import com.example.data.model.OwnerLocal

fun List<GitRepositoryLocal>.mapToRepositoriesDb() =
    map { it.mapToDb() }

fun GitRepositoryLocal.mapToDb() =
    GitRepositoryDb(
        id = id,
        name = name,
        description = description,
        programmingLanguage = programmingLanguage,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        openIssues = openIssues,
        watchersCount = watchersCount,
        owner = owner.mapToDb(),
        contributorsUrl = contributorsUrl
    )

fun GitRepositoryDb.mapToLocal() =
    GitRepositoryLocal(
        id = id,
        name = name,
        description = description,
        programmingLanguage = programmingLanguage,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        openIssues = openIssues,
        watchersCount = watchersCount,
        owner = owner.mapToLocal(),
        contributorsUrl = contributorsUrl
    )

fun OwnerLocal.mapToDb() =
    OwnerDb(
        userName = userName,
        avatarUrl = avatarUrl
    )

fun List<GitRepositoryDbWithFavourite>.mapToRepositoriesLocal() =
    map { it.mapToLocal() }

fun GitRepositoryDbWithFavourite.mapToLocal() =
    GitRepositoryLocalWithFavourite(
        repository = GitRepositoryLocal(
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
        ), isFavourite = favouriteId != null
    )

fun OwnerDb.mapToLocal() =
    OwnerLocal(
        userName = userName,
        avatarUrl = avatarUrl
    )

fun GitRepositoryDetailsLocal.mapToDb() =
    GitRepositoryDetailsDb(
        id = id,
        name = name,
        description = description,
        programmingLanguage = programmingLanguage,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        openIssues = openIssues,
        watchersCount = watchersCount,
        owner = owner.mapToDb(),
        defaultBranch = defaultBranch,
        htmlUrl = htmlUrl,
        contributorsUrl = contributorsUrl,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun GitRepositoryContributorLocal.mapToDb() =
    GitRepositoryContributorDb(
        id = id,
        name = name,
        avatarUrl = avatarUrl
    )

fun List<GitRepositoryContributorLocal>.mapToContributorsDb() =
    map { it.mapToDb() }

fun GitRepositoryDetailsDbWithFavourite.mapToLocal() =
    GitRepositoryDetailsLocalWithFavourite(
        repository = GitRepositoryDetailsLocal(
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
        ),
        isFavourite = favouriteId != null
    )

fun List<GitRepositoryContributorDbWithFavourite>.mapToContributorsLocal() =
    map { it.mapToLocal() }

fun GitRepositoryContributorDbWithFavourite.mapToLocal() =
    GitRepositoryContributorLocalWithFavourite(
        contributor = GitRepositoryContributorLocal(
            id = id,
            name = name,
            avatarUrl = avatarUrl
        ),
        isFavourite = favouriteId != null
    )