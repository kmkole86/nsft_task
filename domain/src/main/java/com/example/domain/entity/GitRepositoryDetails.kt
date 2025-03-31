package com.example.domain.entity

data class GitRepositoryDetails(
    val id: Int,
    val name: String,
    val description: String,
    val programmingLanguage: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val openIssues: Int,
    val watchersCount: Int,
    val owner: GitRepositoryOwner,
    val isFavourite: Boolean,
    val htmlUrl: String,
    val contributorsUrl: String,
    val defaultBranch: String,
    val createdAt: String,
    val updatedAt: String
//    val createdAt: ZonedDateTime,
//    val updatedAt: ZonedDateTime
)