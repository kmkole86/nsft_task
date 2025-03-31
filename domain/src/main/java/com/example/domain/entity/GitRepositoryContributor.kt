package com.example.domain.entity

data class GitRepositoryContributor(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val isFavourite: Boolean
)