package com.example.data.model

data class GitRepositoryLocal(
    val id: Int,
    val name: String,
    val description: String,
    val programmingLanguage: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val openIssues: Int,
    val watchersCount: Int,
    val contributorsUrl: String,
    val owner: OwnerLocal,
)

data class GitRepositoryLocalWithFavourite(
    val repository: GitRepositoryLocal, val isFavourite: Boolean
)