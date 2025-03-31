package com.example.data.model

data class GitRepositoryDetailsLocal(
    val id: Int,
    val name: String,
    val description: String,
    val programmingLanguage: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val openIssues: Int,
    val watchersCount: Int,
    val owner: OwnerLocal,
    val htmlUrl: String,
    val contributorsUrl: String,
    val defaultBranch: String,
    val createdAt: String,
    val updatedAt: String
)

data class GitRepositoryDetailsLocalWithFavourite(
    val repository: GitRepositoryDetailsLocal, val isFavourite: Boolean
)