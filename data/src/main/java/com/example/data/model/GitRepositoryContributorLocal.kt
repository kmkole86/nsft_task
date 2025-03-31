package com.example.data.model

data class GitRepositoryContributorLocal(
    val id: Int,
    val name: String,
    val avatarUrl: String
)

data class GitRepositoryContributorLocalWithFavourite(
    val contributor: GitRepositoryContributorLocal,
    val isFavourite: Boolean
)