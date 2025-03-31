package com.example.nsft_zadatak.model

data class GitRepositoryUiModel(
    val id: Int,
    val name: String,
    val description: String,
    val programmingLanguage: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val openIssues: Int,
    val watchersCount: Int,
    val owner: GitRepositoryOwnerUiModel,
    val contributorsUrl: String,
    val isFavourite: Boolean
)

data class GitRepositoryOwnerUiModel(val userName: String, val avatarUrl: String?)