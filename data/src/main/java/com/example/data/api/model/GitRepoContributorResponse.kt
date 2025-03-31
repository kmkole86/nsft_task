package com.example.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitRepoContributorResponse(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "login")
    val name: String,
    @SerialName(value = "avatar_url")
    val avatarUrl: String
)