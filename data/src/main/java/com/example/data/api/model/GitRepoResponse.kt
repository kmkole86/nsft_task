package com.example.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitRepositoryResponse(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "description")
    val description: String,
    @SerialName(value = "language")
    val programmingLanguage: String,
    @SerialName(value = "stargazers_count")
    val stargazersCount: Int,
    @SerialName(value = "forks_count")
    val forksCount: Int,
    @SerialName(value = "open_issues")
    val openIssues: Int,
    @SerialName(value = "watchers")
    val watchersCount: Int,
    @SerialName(value = "contributors_url")
    val contributorsUrl: String,
    @SerialName(value = "owner")
    val owner: OwnerResponse,
)

@Serializable
data class OwnerResponse(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "login")
    val userName: String,
    @SerialName(value = "avatar_url")
    val avatarUrl: String?
)