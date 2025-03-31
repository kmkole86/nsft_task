package com.example.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitRepositoryDetailsResponse(
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
    @SerialName(value = "owner")
    val owner: OwnerResponse,
    @SerialName(value = "html_url")
    val htmlUrl: String,
    @SerialName(value = "contributors_url")
    val contributorsUrl: String,
    @SerialName(value = "default_branch")
    val defaultBranch: String,
    @SerialName(value = "created_at")
    val createdAt: String,
    @SerialName(value = "updated_at")
    val updatedAt: String
)