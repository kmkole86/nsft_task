package com.example.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitRepoPageResponse(
    @SerialName(value = "total_count")
    val totalCount: Int,
    @SerialName(value = "items")
    val items: List<GitRepositoryResponse>
)