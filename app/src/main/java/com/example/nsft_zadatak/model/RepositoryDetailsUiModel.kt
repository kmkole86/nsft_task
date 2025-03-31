package com.example.nsft_zadatak.model

data class GitRepositoryDetailsUiModel(
    val id: Int,
    val name: String,
    val description: String,
    val programmingLanguage: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val openIssues: Int,
    val watchersCount: Int,
    val owner: GitRepositoryOwnerUiModel,
    val isFavourite: Boolean,
    val htmlUrl: String,
    val contributorsUrl: String,
    val defaultBranch: String,
    val createdAt: String,
    val updatedAt: String
//    val createdAt: DateTime,
//    val updatedAt: DateTime
)