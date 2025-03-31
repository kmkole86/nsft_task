package com.example.domain.entity

data class GitRepositoryEntity(
    val id: Int,
    val name: String,
    val description: String,
    val programmingLanguage: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val openIssues: Int,
    val watchersCount: Int,
    val owner: GitRepositoryOwner,
    val contributorsUrl: String,
    val isFavourite: Boolean
)

data class GitRepositoryOwner(val userName: String, val avatarUrl: String?)